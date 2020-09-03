package com.ekoo.themealdb

import android.view.View

fun View.visibleWhen(visible: Boolean){

    if (visible) this.visibility = View.VISIBLE
    else this.visibility = View.GONE

}