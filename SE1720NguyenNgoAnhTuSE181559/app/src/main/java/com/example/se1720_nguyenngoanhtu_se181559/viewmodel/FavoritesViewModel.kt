package com.example.se1720_nguyenngoanhtu_se181559.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se1720_nguyenngoanhtu_se181559.data.model.Meal
import com.example.se1720_nguyenngoanhtu_se181559.data.repository.MealRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: MealRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    val favoriteMeals: StateFlow<List<Meal>> = combine(
        repository.getFavoriteMeals(),
        _searchQuery,
        _selectedCategory
    ) { favorites, query, category ->
        filterMeals(favorites, query, category)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val categories: StateFlow<List<String>> = repository.getFavoriteCategories()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onCategorySelected(category: String?) {
        _selectedCategory.value = category
    }

    fun removeFavorite(mealId: String) {
        viewModelScope.launch {
            repository.removeFavorite(mealId)
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
