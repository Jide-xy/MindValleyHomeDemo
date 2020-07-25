package com.example.mindvalleytest.util.imageloading

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import javax.inject.Inject

class GlideImageLoader @Inject constructor() : ImageLoader {
    override fun load(
        imageView: ImageView,
        url: String,
        placeholder: Int,
        crossFade: Boolean,
        isCircle: Boolean
    ) {
        var builder = Glide.with(imageView)
            .load(url)
        if (placeholder != View.NO_ID) {
            builder = builder.placeholder(placeholder)
        }
        if (crossFade) {
            builder = builder.transition(DrawableTransitionOptions.withCrossFade())
        }
        if (isCircle) {
            builder = builder.optionalCircleCrop()
        }
        builder.into(imageView)
    }
}