package com.example.mindvalleytest.di

import android.content.Context
import com.example.mindvalleytest.network.MindValleyService
import com.example.mindvalleytest.repository.IMindValleyRepository
import com.example.mindvalleytest.repository.MindValleyRepository
import com.example.mindvalleytest.room.MindValleyDb
import com.example.mindvalleytest.util.DefaultDispatcherProvider
import com.example.mindvalleytest.util.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideService(): MindValleyService {
        return Retrofit.Builder()
            .baseUrl("https://pastebin.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(MindValleyService::class.java)
    }

    @Singleton
    @Provides
    fun provideLocalDb(@ApplicationContext app: Context): MindValleyDb {
        return MindValleyDb.getInstance(app)
    }
}

@Module
@InstallIn(ApplicationComponent::class)
abstract class BindingModule {
    @Singleton
    @Binds
    abstract fun provideRepository(
        mindValleyRepository: MindValleyRepository
    ): IMindValleyRepository

    @Singleton
    @Binds
    abstract fun provideCoroutineDispatcher(
        dispatcherProvider: DefaultDispatcherProvider
    ): DispatcherProvider
}