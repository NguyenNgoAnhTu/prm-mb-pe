package com.example.se1720_nguyenngoanhtu_se181559.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {

    @GET("search.php?s=")
    suspend fun getAllMeals(): MealsResponse

    @GET("lookup.php")
    suspend fun getMealById(@Query("i") id: String): MealsResponse

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealsResponse

    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse

    companion object {
        private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

        fun create(): MealApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MealApiService::class.java)
        }
    }
}
