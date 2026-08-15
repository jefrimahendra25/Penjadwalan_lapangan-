package com.penjadwalan.lapangan

import android.app.Application
import androidx.room.Room
import com.penjadwalan.lapangan.data.AppDatabase
import com.penjadwalan.lapangan.data.Booking
import com.penjadwalan.lapangan.data.BookingRepository
import com.penjadwalan.lapangan.data.BookingStatus
import com.penjadwalan.lapangan.data.Field
import com.penjadwalan.lapangan.data.FieldRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PenjadwalanApplication : Application() {
    
    lateinit var database: AppDatabase
        private set
    
    lateinit var fieldRepository: FieldRepository
        private set
    
    lateinit var bookingRepository: BookingRepository
        private set
    
    override fun onCreate() {
        super.onCreate()
        
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "penjadwalan_database"
        ).build()
        
        fieldRepository = FieldRepository(database.fieldDao())
        bookingRepository = BookingRepository(database.bookingDao())
        
        // Insert sample data
        insertSampleData()
    }
    
    private fun insertSampleData() {
        CoroutineScope(Dispatchers.IO).launch {
            // Check if data already exists
            val existingFields = database.fieldDao().getAllFields()
            existingFields.collect { fields ->
                if (fields.isEmpty()) {
                    val sampleFields = listOf(
                        Field(
                            name = "Lapangan Futsal Merdeka",
                            type = "Futsal",
                            location = "Jl. Merdeka No. 123, Jakarta",
                            pricePerHour = 150000.0,
                            description = "Lapangan futsal standar internasional dengan rumput sintetis berkualitas tinggi. Dilengkapi dengan pencahayaan yang baik dan sistem drainase modern.",
                            facilities = "Rumput Sintetis, Lampu LED, Ruang Ganti, Toilet, Parkir Luas, Kantin",
                            isOpen = true
                        ),
                        Field(
                            name = "Gor Basket Bung Karno",
                            type = "Basket",
                            location = "Jl. Pemuda No. 45, Surabaya",
                            pricePerHour = 200000.0,
                            description = "Lapangan basket indoor dengan lantai parquet profesional. Cocok untuk pertandingan dan latihan.",
                            facilities = "Lantai Parquet, AC, Papan Skor Elektronik, Ruang Ganti, Toilet",
                            isOpen = true
                        ),
                        Field(
                            name = "Lapangan Voli Pantai Indah",
                            type = "Voli",
                            location = "Jl. Pantai Indah No. 78, Bali",
                            pricePerHour = 100000.0,
                            description = "Lapangan voli pantai dengan pasir putih dan pemandangan laut yang indah. Tempat yang sempurna untuk olahraga dan rekreasi.",
                            facilities = "Pasir Putih, Payung, Kursi Pantai, Toilet, Kantin",
                            isOpen = true
                        ),
                        Field(
                            name = "Gelanggang Badminton Champion",
                            type = "Badminton",
                            location = "Jl. Juara No. 56, Bandung",
                            pricePerHour = 75000.0,
                            description = "Lapangan badminton profesional dengan karpet standar internasional. Dilengkapi dengan pencahayaan yang optimal.",
                            facilities = "Karpet Pro, Lampu LED, Net Profesional, Ruang Ganti, Toilet",
                            isOpen = true
                        ),
                        Field(
                            name = "Lapangan Sepak Bola Gelora",
                            type = "Sepak Bola",
                            location = "Jl. Gelora No. 90, Yogyakarta",
                            pricePerHour = 500000.0,
                            description = "Lapangan sepak bola ukuran standar dengan rumput alami yang terawat. Cocok untuk pertandingan resmi dan latihan tim.",
                            facilities = "Rumput Alami, Lampu, Ruang Ganti, Toilet, Parkir, Kantin",
                            isOpen = true
                        ),
                        Field(
                            name = "Lapangan Tenis Royal",
                            type = "Tenis",
                            location = "Jl. Kerajaan No. 23, Medan",
                            pricePerHour = 175000.0,
                            description = "Lapangan tenis hard court dengan permukaan berkualitas tinggi. Dilengkapi dengan fasilitas lengkap untuk pemain profesional.",
                            facilities = "Hard Court, Lampu, Net Profesional, Ruang Ganti, Toilet, Pro Shop",
                            isOpen = true
                        )
                    )
                    
                    database.fieldDao().insertFields(sampleFields)
                }
            }
        }
    }
}
