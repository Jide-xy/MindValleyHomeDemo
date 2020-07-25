package com.example.mindvalleytest.di

import com.example.mindvalleytest.util.imageloading.GlideImageLoader
import com.example.mindvalleytest.util.imageloading.ImageLoader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class ImageLoaderModule {

    @Singleton
    @Binds
    abstract fun provideImageLoader(glideImageLoader: GlideImageLoader): ImageLoader
}