package com.ekoo.themealdb

import retrofit2.http.GET

interface MealServices {

    @GET("api/json/v1/1/random.php")
    suspend fun fetchRandomMeal(): MealModel

}