package com.example.se1720_nguyenngoanhtu_se181559

import android.app.Application
import com.example.se1720_nguyenngoanhtu_se181559.data.api.MealApiService
import com.example.se1720_nguyenngoanhtu_se181559.data.database.MealDatabase
import com.example.se1720_nguyenngoanhtu_se181559.data.repository.MealRepository

class MealApplication : Application() {
    lateinit var repository: MealRepository

    override fun onCreate() {
        super.onCreate()

        val database = MealDatabase.getDatabase(this)
        val apiService = MealApiService.create()
        repository = MealRepository(apiService, database.mealDao())
    }
}
