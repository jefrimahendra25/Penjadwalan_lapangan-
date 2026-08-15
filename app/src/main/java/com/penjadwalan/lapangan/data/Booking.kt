package com.penjadwalan.lapangan.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "bookings",
    foreignKeys = [
        ForeignKey(
            entity = Field::class,
            parentColumns = ["id"],
            childColumns = ["fieldId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("fieldId")]
)
data class Booking(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fieldId: Long,
    val customerName: String,
    val customerPhone: String,
    val date: String, // Format: yyyy-MM-dd
    val startTime: String, // Format: HH:mm
    val endTime: String, // Format: HH:mm
    val totalPrice: Double,
    val status: BookingStatus = BookingStatus.CONFIRMED,
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

enum class BookingStatus {
    PENDING,
    CONFIRMED,
    CANCELLED,
    COMPLETED
}
