package com.example.se1720_nguyenngoanhtu_se181559.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.se1720_nguyenngoanhtu_se181559.data.api.MealApiService
import com.example.se1720_nguyenngoanhtu_se181559.data.database.MealDatabase
import com.example.se1720_nguyenngoanhtu_se181559.data.repository.MealRepository

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    private val repository: MealRepository by lazy {
        val database = MealDatabase.getDatabase(context)
        val apiService = MealApiService.create()
        MealRepository(apiService, database.mealDao())
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MealListViewModel::class.java) -> {
                MealListViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FavoritesViewModel::class.java) -> {
                FavoritesViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

class MealDetailViewModelFactory(
    private val context: Context,
    private val mealId: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealDetailViewModel::class.java)) {
            val database = MealDatabase.getDatabase(context)
            val apiService = MealApiService.create()
            val repository = MealRepository(apiService, database.mealDao())
            return MealDetailViewModel(repository, mealId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
