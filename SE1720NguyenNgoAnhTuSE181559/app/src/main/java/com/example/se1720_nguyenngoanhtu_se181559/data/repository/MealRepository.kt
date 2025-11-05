package com.example.se1720_nguyenngoanhtu_se181559.data.repository

import com.example.se1720_nguyenngoanhtu_se181559.data.api.MealApiService
import com.example.se1720_nguyenngoanhtu_se181559.data.database.FavoriteEntity
import com.example.se1720_nguyenngoanhtu_se181559.data.database.MealDao
import com.example.se1720_nguyenngoanhtu_se181559.data.model.Meal
import com.example.se1720_nguyenngoanhtu_se181559.data.model.toDomain
import com.example.se1720_nguyenngoanhtu_se181559.data.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class MealRepository(
    private val apiService: MealApiService,
    private val mealDao: MealDao
) {

    suspend fun refreshMeals(): Result<Unit> {
        return try {
            val response = apiService.getAllMeals()
            response.meals?.let { meals ->
                val entities = meals.map { it.toEntity() }
                mealDao.insertMeals(entities)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getMeals(): Flow<List<Meal>> {
        return mealDao.getAllMeals().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun getMealById(id: String): Result<Meal?> {
        return try {
            val cachedMeal = mealDao.getMealById(id).firstOrNull()
            if (cachedMeal != null) {
                return Result.success(cachedMeal.toDomain())
            }

            val response = apiService.getMealById(id)
            val meal = response.meals?.firstOrNull()
            if (meal != null) {
                mealDao.insertMeal(meal.toEntity())
                Result.success(meal.toDomain())
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun searchMeals(query: String): Flow<List<Meal>> {
        return if (query.isBlank()) {
            getMeals()
        } else {
            mealDao.searchMeals(query).map { entities ->
                entities.map { it.toDomain() }
            }
        }
    }

    fun getMealsByCategory(category: String): Flow<List<Meal>> {
        return mealDao.getMealsByCategory(category).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    fun getAllCategories(): Flow<List<String>> {
        return mealDao.getAllCategories()
    }

    fun isFavorite(id: String): Flow<Boolean> {
        return mealDao.isFavorite(id)
    }

    suspend fun addFavorite(id: String) {
        mealDao.insertFavorite(FavoriteEntity(idMeal = id))
    }

    suspend fun removeFavorite(id: String) {
        mealDao.deleteFavorite(id)
    }

    suspend fun toggleFavorite(id: String) {
        val isFav = mealDao.isFavorite(id).firstOrNull() ?: false
        if (isFav) {
            removeFavorite(id)
        } else {
            addFavorite(id)
        }
    }

    fun getFavoriteMeals(): Flow<List<Meal>> {
        return mealDao.getFavoriteMeals().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    fun searchFavoriteMeals(query: String): Flow<List<Meal>> {
        return if (query.isBlank()) {
            getFavoriteMeals()
        } else {
            mealDao.searchFavoriteMeals(query).map { entities ->
                entities.map { it.toDomain() }
            }
        }
    }

    fun getFavoriteMealsByCategory(category: String): Flow<List<Meal>> {
        return mealDao.getFavoriteMealsByCategory(category).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    fun getFavoriteCategories(): Flow<List<String>> {
        return mealDao.getFavoriteCategories()
    }
}
