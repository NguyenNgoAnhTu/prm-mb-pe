package com.example.se1720_nguyenngoanhtu_se181559.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se1720_nguyenngoanhtu_se181559.data.model.Meal
import com.example.se1720_nguyenngoanhtu_se181559.data.repository.MealRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MealListViewModel(
    private val repository: MealRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    val meals: StateFlow<List<Meal>> = combine(
        repository.getMeals(),
        _searchQuery,
        _selectedCategory
    ) { allMeals, query, category ->
        filterMeals(allMeals, query, category)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val categories: StateFlow<List<String>> = repository.getAllCategories()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun refreshMeals() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            repository.refreshMeals()
                .onSuccess {
                    _errorMessage.value = null
                }
                .onFailure { exception ->
                    _errorMessage.value = exception.message ?: "Failed to load meals"
                }

            _isLoading.value = false
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onCategorySelected(category: String?) {
        _selectedCategory.value = category
    }

    fun isFavorite(mealId: String): Flow<Boolean> {
        return repository.isFavorite(mealId)
    }

    fun toggleFavorite(mealId: String) {
        viewModelScope.launch {
            repository.toggleFavorite(mealId)
        }
    }

    private fun filterMeals(
        meals: List<Meal>,
        query: String,
        category: String?
    ): List<Meal> {
        var filtered = meals

        // Filter by category
        if (category != null) {
            filtered = filtered.filter { it.strCategory == category }
        }

        // Filter by search query
        if (query.isNotBlank()) {
            filtered = filtered.filter { meal ->
                meal.strMeal.contains(query, ignoreCase = true) ||
                        meal.strCategory?.contains(query, ignoreCase = true) == true
            }
        }

        return filtered
    }
}
