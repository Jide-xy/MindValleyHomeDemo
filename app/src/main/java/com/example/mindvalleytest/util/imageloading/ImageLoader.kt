package com.example.mindvalleytest.util.imageloading

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes

interface ImageLoader {

    fun load(
        imageView: ImageView,
        url: String,
        @DrawableRes placeholder: Int = View.NO_ID,
        crossFade: Boolean = false,
        isCircle: Boolean = false
    )
}