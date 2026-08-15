package com.penjadwalan.lapangan.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {
    @Query("SELECT * FROM bookings")
    fun getAllBookings(): Flow<List<Booking>>

    @Query("SELECT * FROM bookings WHERE id = :bookingId")
    suspend fun getBookingById(bookingId: Long): Booking?

    @Query("SELECT * FROM bookings WHERE fieldId = :fieldId")
    fun getBookingsByField(fieldId: Long): Flow<List<Booking>>

    @Query("SELECT * FROM bookings WHERE fieldId = :fieldId AND date = :date")
    fun getBookingsByFieldAndDate(fieldId: Long, date: String): Flow<List<Booking>>

    @Query("SELECT * FROM bookings WHERE date = :date")
    fun getBookingsByDate(date: String): Flow<List<Booking>>

    @Query("SELECT * FROM bookings WHERE customerName LIKE '%' || :query || '%'")
    fun searchBookings(query: String): Flow<List<Booking>>

    @Query("SELECT * FROM bookings WHERE status = :status")
    fun getBookingsByStatus(status: BookingStatus): Flow<List<Booking>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: Booking): Long

    @Update
    suspend fun updateBooking(booking: Booking)

    @Delete
    suspend fun deleteBooking(booking: Booking)

    @Query("DELETE FROM bookings")
    suspend fun deleteAllBookings()
}
