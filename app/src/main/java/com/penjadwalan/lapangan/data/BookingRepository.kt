package com.penjadwalan.lapangan.data

import kotlinx.coroutines.flow.Flow

class BookingRepository(private val bookingDao: BookingDao) {
    
    fun getAllBookings(): Flow<List<Booking>> = bookingDao.getAllBookings()
    
    suspend fun getBookingById(bookingId: Long): Booking? = bookingDao.getBookingById(bookingId)
    
    fun getBookingsByField(fieldId: Long): Flow<List<Booking>> = bookingDao.getBookingsByField(fieldId)
    
    fun getBookingsByFieldAndDate(fieldId: Long, date: String): Flow<List<Booking>> = 
        bookingDao.getBookingsByFieldAndDate(fieldId, date)
    
    fun getBookingsByDate(date: String): Flow<List<Booking>> = bookingDao.getBookingsByDate(date)
    
    fun searchBookings(query: String): Flow<List<Booking>> = bookingDao.searchBookings(query)
    
    fun getBookingsByStatus(status: BookingStatus): Flow<List<Booking>> = 
        bookingDao.getBookingsByStatus(status)
    
    suspend fun insertBooking(booking: Booking): Long = bookingDao.insertBooking(booking)
    
    suspend fun updateBooking(booking: Booking) = bookingDao.updateBooking(booking)
    
    suspend fun deleteBooking(booking: Booking) = bookingDao.deleteBooking(booking)
    
    suspend fun deleteAllBookings() = bookingDao.deleteAllBookings()
}
