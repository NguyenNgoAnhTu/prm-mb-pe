package com.example.se1720_nguyenngoanhtu_se181559.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Query("SELECT * FROM meals")
    fun getAllMeals(): Flow<List<MealEntity>>

    @Query("SELECT * FROM meals WHERE idMeal = :id")
    fun getMealById(id: String): Flow<MealEntity?>

    @Query("SELECT * FROM meals WHERE strMeal LIKE '%' || :query || '%' OR strCategory LIKE '%' || :query || '%'")
    fun searchMeals(query: String): Flow<List<MealEntity>>

    @Query("SELECT * FROM meals WHERE strCategory = :category")
    fun getMealsByCategory(category: String): Flow<List<MealEntity>>

    @Query("SELECT DISTINCT strCategory FROM meals WHERE strCategory IS NOT NULL ORDER BY strCategory")
    fun getAllCategories(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MealEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeals(meals: List<MealEntity>)

    @Query("DELETE FROM meals")
    suspend fun deleteAllMeals()

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorites WHERE idMeal = :id")
    fun getFavoriteById(id: String): Flow<FavoriteEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE idMeal = :id")
    suspend fun deleteFavorite(id: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE idMeal = :id)")
    fun isFavorite(id: String): Flow<Boolean>

    @Query("""
        SELECT m.* FROM meals m
        INNER JOIN favorites f ON m.idMeal = f.idMeal
        ORDER BY f.addedAt DESC
    """)
    fun getFavoriteMeals(): Flow<List<MealEntity>>

    @Query("""
        SELECT m.* FROM meals m
        INNER JOIN favorites f ON m.idMeal = f.idMeal
        WHERE m.strMeal LIKE '%' || :query || '%' OR m.strCategory LIKE '%' || :query || '%'
        ORDER BY f.addedAt DESC
    """)
    fun searchFavoriteMeals(query: String): Flow<List<MealEntity>>

    @Query("""
        SELECT m.* FROM meals m
        INNER JOIN favorites f ON m.idMeal = f.idMeal
        WHERE m.strCategory = :category
        ORDER BY f.addedAt DESC
    """)
    fun getFavoriteMealsByCategory(category: String): Flow<List<MealEntity>>

    @Query("""
        SELECT DISTINCT m.strCategory FROM meals m
        INNER JOIN favorites f ON m.idMeal = f.idMeal
        WHERE m.strCategory IS NOT NULL
        ORDER BY m.strCategory
    """)
    fun getFavoriteCategories(): Flow<List<String>>
}
