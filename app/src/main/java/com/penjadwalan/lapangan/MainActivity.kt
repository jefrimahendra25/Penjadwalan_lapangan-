package com.penjadwalan.lapangan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.penjadwalan.lapangan.data.Booking
import com.penjadwalan.lapangan.data.Field
import com.penjadwalan.lapangan.ui.screens.*
import com.penjadwalan.lapangan.ui.theme.PenjadwalanLapanganTheme
import com.penjadwalan.lapangan.viewmodel.BookingViewModel
import com.penjadwalan.lapangan.viewmodel.FieldViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PenjadwalanLapanganTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PenjadwalanNavHost()
                }
            }
        }
    }
}

@Composable
fun PenjadwalanNavHost(
    navController: NavHostController = rememberNavController()
) {
    val application = androidx.compose.ui.platform.LocalContext.current.applicationContext as PenjadwalanApplication
    val fieldViewModel: FieldViewModel = viewModel(
        factory = FieldViewModel.Factory(application.fieldRepository)
    )
    val bookingViewModel: BookingViewModel = viewModel(
        factory = BookingViewModel.Factory(application.bookingRepository)
    )
    
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onFieldListClick = { navController.navigate("field_list") },
                onBookingListClick = { navController.navigate("booking_list") }
            )
        }
        
        composable("field_list") {
            FieldListScreen(
                viewModel = fieldViewModel,
                onFieldClick = { field ->
                    navController.navigate("field_detail/${field.id}")
                }
            )
        }
        
        composable("field_detail/{fieldId}") { backStackEntry ->
            val fieldId = backStackEntry.arguments?.getString("fieldId")?.toLongOrNull() ?: return@composable
            var field by remember { mutableStateOf<Field?>(null) }
            
            LaunchedEffect(fieldId) {
                field = fieldViewModel.getFieldById(fieldId)
            }
            
            field?.let { selectedField ->
                FieldDetailScreen(
                    field = selectedField,
                    onBackClick = { navController.popBackStack() },
                    onBookClick = { 
                        navController.navigate("booking/${selectedField.id}")
                    }
                )
            }
        }
        
        composable("booking/{fieldId}") { backStackEntry ->
            val fieldId = backStackEntry.arguments?.getString("fieldId")?.toLongOrNull() ?: return@composable
            var field by remember { mutableStateOf<Field?>(null) }
            
            LaunchedEffect(fieldId) {
                field = fieldViewModel.getFieldById(fieldId)
            }
            
            field?.let { selectedField ->
                BookingScreen(
                    viewModel = bookingViewModel,
                    field = selectedField,
                    onBackClick = { navController.popBackStack() },
                    onBookingSuccess = {
                        navController.popBackStack()
                        navController.navigate("booking_list")
                    }
                )
            }
        }
        
        composable("booking_list") {
            BookingListScreen(
                viewModel = bookingViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
