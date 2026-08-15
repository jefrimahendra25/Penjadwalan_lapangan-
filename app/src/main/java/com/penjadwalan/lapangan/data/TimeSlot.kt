package com.penjadwalan.lapangan.data

data class TimeSlot(
    val hour: Int, // 0-23
    val isAvailable: Boolean,
    val price: Double
) {
    val timeString: String
        get() = String.format("%02d:00", hour)
}
