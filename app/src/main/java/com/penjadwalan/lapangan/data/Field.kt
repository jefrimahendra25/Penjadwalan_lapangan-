package com.penjadwalan.lapangan.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fields")
data class Field(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: String, // Futsal, Basket, Voli, Badminton, dll
    val location: String,
    val pricePerHour: Double,
    val description: String,
    val imageUrl: String = "",
    val facilities: String = "", // List fasilitas dipisahkan koma
    val isOpen: Boolean = true
)
