package com.ekoo.themealdb

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@ExperimentalCoroutinesApi
class MainRepository(private val mealServices: MealServices = ServiceBuilder.build()) {

    val apiCallState: StateFlow<MainViewState> get() = _apiCallState
    private val _apiCallState = MutableStateFlow<MainViewState>(Idle)
    var fetchJob : Job? = null

    fun getRandomMeal(scope: CoroutineScope){
        _apiCallState.value = FetchingData
        fetchJob?.cancel()
        fetchJob = scope.launch(Dispatchers.IO){
            try {
                val randomMeal = mealServices.fetchRandomMeal()
                _apiCallState.value = SuccessFetchingData(randomMeal)
            }catch (exception: Exception){
                _apiCallState.value = FailedFetchingData(exception)
            }
        }
    }

}