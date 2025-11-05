package com.example.se1720_nguyenngoanhtu_se181559.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey
    val idMeal: String,
    val addedAt: Long = System.currentTimeMillis()
)
