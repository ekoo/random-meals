package com.ekoo.themealdb

import java.lang.Exception

sealed class MainViewState
object Idle : MainViewState()
object FetchingData : MainViewState()
data class SuccessFetchingData(val data: MealModel) : MainViewState()
data class FailedFetchingData(val exception: Exception) : MainViewState()
