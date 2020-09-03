package com.ekoo.themealdb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainViewModel(private val mainRepository: MainRepository = MainRepository()): ViewModel() {

    init {
        mainRepository.getRandomMeal(viewModelScope)
    }

    val mainState = mainRepository.apiCallState

    fun reloadState() = mainRepository.getRandomMeal(viewModelScope)
}