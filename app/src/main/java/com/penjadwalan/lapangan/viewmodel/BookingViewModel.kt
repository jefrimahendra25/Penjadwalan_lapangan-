package com.penjadwalan.lapangan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.penjadwalan.lapangan.data.Booking
import com.penjadwalan.lapangan.data.BookingRepository
import com.penjadwalan.lapangan.data.BookingStatus
import com.penjadwalan.lapangan.data.Field
import com.penjadwalan.lapangan.data.TimeSlot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class BookingViewModel(private val repository: BookingRepository) : ViewModel() {
    
    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
    val bookings: StateFlow<List<Booking>> = _bookings.asStateFlow()
    
    private val _selectedDate = MutableStateFlow(getTodayDate())
    val selectedDate: StateFlow<String> = _selectedDate.asStateFlow()
    
    private val _selectedField = MutableStateFlow<Field?>(null)
    val selectedField: StateFlow<Field?> = _selectedField.asStateFlow()
    
    private val _timeSlots = MutableStateFlow<List<TimeSlot>>(emptyList())
    val timeSlots: StateFlow<List<TimeSlot>> = _timeSlots.asStateFlow()
    
    private val _selectedTimeSlots = MutableStateFlow<List<TimeSlot>>(emptyList())
    val selectedTimeSlots: StateFlow<List<TimeSlot>> = _selectedTimeSlots.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        loadBookings()
    }
    
    fun loadBookings() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getAllBookings().collect { bookingList ->
                _bookings.value = bookingList
                _isLoading.value = false
            }
        }
    }
    
    fun selectField(field: Field) {
        _selectedField.value = field
        generateTimeSlots()
        loadBookingsForFieldAndDate()
    }
    
    fun selectDate(date: String) {
        _selectedDate.value = date
        loadBookingsForFieldAndDate()
    }
    
    private fun loadBookingsForFieldAndDate() {
        val field = _selectedField.value ?: return
        viewModelScope.launch {
            repository.getBookingsByFieldAndDate(field.id, _selectedDate.value).collect { bookingList ->
                updateTimeSlotsAvailability(bookingList)
            }
        }
    }
    
    private fun generateTimeSlots() {
        val field = _selectedField.value ?: return
        val slots = mutableListOf<TimeSlot>()
        
        // Generate slots from 6:00 to 22:00
        for (hour in 6..22) {
            slots.add(TimeSlot(hour, true, field.pricePerHour))
        }
        
        _timeSlots.value = slots
    }
    
    private fun updateTimeSlotsAvailability(bookings: List<Booking>) {
        val updatedSlots = _timeSlots.value.map { slot ->
            val isBooked = bookings.any { booking ->
                val bookingStartHour = booking.startTime.substringBefore(":").toInt()
                val bookingEndHour = booking.endTime.substringBefore(":").toInt()
                slot.hour in bookingStartHour until bookingEndHour
            }
            slot.copy(isAvailable = !isBooked)
        }
        _timeSlots.value = updatedSlots
    }
    
    fun toggleTimeSlot(slot: TimeSlot) {
        val currentSelection = _selectedTimeSlots.value.toMutableList()
        if (currentSelection.contains(slot)) {
            currentSelection.remove(slot)
        } else {
            if (slot.isAvailable) {
                currentSelection.add(slot)
            }
        }
        _selectedTimeSlots.value = currentSelection.sortedBy { it.hour }
    }
    
    fun createBooking(
        customerName: String,
        customerPhone: String,
        notes: String = ""
    ): Boolean {
        val field = _selectedField.value ?: return false
        val selectedSlots = _selectedTimeSlots.value
        if (selectedSlots.isEmpty()) return false
        
        val totalPrice = selectedSlots.sumOf { it.price }
        val startTime = String.format("%02d:00", selectedSlots.first().hour)
        val endTime = String.format("%02d:00", selectedSlots.last().hour + 1)
        
        viewModelScope.launch {
            val booking = Booking(
                fieldId = field.id,
                customerName = customerName,
                customerPhone = customerPhone,
                date = _selectedDate.value,
                startTime = startTime,
                endTime = endTime,
                totalPrice = totalPrice,
                status = BookingStatus.CONFIRMED,
                notes = notes
            )
            repository.insertBooking(booking)
            _selectedTimeSlots.value = emptyList()
            loadBookingsForFieldAndDate()
        }
        
        return true
    }
    
    fun updateBookingStatus(booking: Booking, status: BookingStatus) {
        viewModelScope.launch {
            repository.updateBooking(booking.copy(status = status))
        }
    }
    
    fun deleteBooking(booking: Booking) {
        viewModelScope.launch {
            repository.deleteBooking(booking)
        }
    }
    
    fun getBookingsByDate(date: String) {
        viewModelScope.launch {
            repository.getBookingsByDate(date).collect { bookingList ->
                _bookings.value = bookingList
            }
        }
    }
    
    fun searchBookings(query: String) {
        viewModelScope.launch {
            repository.searchBookings(query).collect { bookingList ->
                _bookings.value = bookingList
            }
        }
    }
    
    companion object {
        fun getTodayDate(): String {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return formatter.format(Date())
        }
        
        fun formatDate(date: Date): String {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return formatter.format(date)
        }
        
        fun formatDisplayDate(dateString: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
            val date = inputFormat.parse(dateString)
            return outputFormat.format(date ?: Date())
        }
    }
}
