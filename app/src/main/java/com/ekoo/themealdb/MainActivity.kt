package com.ekoo.themealdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewStub
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_network_error.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val mainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    private lateinit var loadingView: ViewStub
    private lateinit var errorView: ViewStub

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadingView = findViewById(R.id.loadingViewStub)
        errorView = findViewById(R.id.errorViewStub)

        lifecycleScope.launch {
            mainViewModel.mainState.collect{ state ->
                setupView(state)
                updateView(state)
            }
        }
    }

    private fun setupView(state: MainViewState) {
        loadingView.visibleWhen(state is FetchingData)
        errorView.visibleWhen(state is FailedFetchingData)
    }

    private fun updateView(state: MainViewState) {
        when(state){

            is FailedFetchingData -> {
                errorMessage.text = state.exception.message
                retryButton.setOnClickListener {
                    mainViewModel.reloadState()
                }
            }

            is SuccessFetchingData -> {
                val meal = state.data.meals[0]
                mealTitle.text = meal.strMeal
                Picasso.get().load(meal.strMealThumb).into(mealImage)
                getMealButton.setOnClickListener {
                    mainViewModel.reloadState()
                }
            }
        }
    }
}