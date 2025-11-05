package com.example.se1720_nguyenngoanhtu_se181559.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se1720_nguyenngoanhtu_se181559.data.model.Meal
import com.example.se1720_nguyenngoanhtu_se181559.data.repository.MealRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MealDetailViewModel(
    private val repository: MealRepository,
    private val mealId: String
) : ViewModel() {

    private val _meal = MutableStateFlow<Meal?>(null)
    val meal: StateFlow<Meal?> = _meal.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    val isFavorite: StateFlow<Boolean> = repository.isFavorite(mealId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun loadMeal(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            repository.getMealById(id)
                .onSuccess { meal ->
                    _meal.value = meal
                    _errorMessage.value = if (meal == null) "Meal not found" else null
                }
                .onFailure { exception ->
                    _errorMessage.value = exception.message ?: "Failed to load meal details"
                }

            _isLoading.value = false
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            repository.toggleFavorite(mealId)
        }
    }
}
