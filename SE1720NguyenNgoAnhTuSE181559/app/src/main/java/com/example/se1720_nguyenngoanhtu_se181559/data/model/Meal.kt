package com.example.se1720_nguyenngoanhtu_se181559.data.model

import com.example.se1720_nguyenngoanhtu_se181559.data.api.MealDto
import com.example.se1720_nguyenngoanhtu_se181559.data.database.MealEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strCategory: String?,
    val strArea: String?,
    val strInstructions: String?,
    val strMealThumb: String?,
    val strTags: String?,
    val strYoutube: String?,
    val ingredients: List<Pair<String, String>>
)

fun MealDto.toEntity(): MealEntity {
    val gson = Gson()
    val ingredientsJson = gson.toJson(this.getIngredientsList())

    return MealEntity(
        idMeal = this.idMeal,
        strMeal = this.strMeal,
        strCategory = this.strCategory,
        strArea = this.strArea,
        strInstructions = this.strInstructions,
        strMealThumb = this.strMealThumb,
        strTags = this.strTags,
        strYoutube = this.strYoutube,
        ingredients = ingredientsJson
    )
}

fun MealEntity.toDomain(): Meal {
    val gson = Gson()
    val ingredientType = object : TypeToken<List<Pair<String, String>>>() {}.type
    val ingredientsList: List<Pair<String, String>> = gson.fromJson(this.ingredients, ingredientType)

    return Meal(
        idMeal = this.idMeal,
        strMeal = this.strMeal,
        strCategory = this.strCategory,
        strArea = this.strArea,
        strInstructions = this.strInstructions,
        strMealThumb = this.strMealThumb,
        strTags = this.strTags,
        strYoutube = this.strYoutube,
        ingredients = ingredientsList
    )
}

fun MealDto.toDomain(): Meal {
    return Meal(
        idMeal = this.idMeal,
        strMeal = this.strMeal,
        strCategory = this.strCategory,
        strArea = this.strArea,
        strInstructions = this.strInstructions,
        strMealThumb = this.strMealThumb,
        strTags = this.strTags,
        strYoutube = this.strYoutube,
        ingredients = this.getIngredientsList()
    )
}
