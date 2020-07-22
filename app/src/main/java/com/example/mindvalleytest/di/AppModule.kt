package com.example.mindvalleytest.di

import com.example.mindvalleytest.MindValleyApplication
import com.example.mindvalleytest.network.MindValleyService
import com.example.mindvalleytest.repository.IMindValleyRepository
import com.example.mindvalleytest.repository.MindValleyRepository
import com.example.mindvalleytest.room.MindValleyDb
import com.example.mindvalleytest.util.DefaultDispatcherProvider
import com.example.mindvalleytest.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

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
    fun provideLocalDb(app: MindValleyApplication): MindValleyDb {
        return MindValleyDb.getInstance(app)
    }

    @Singleton
    @Provides
    fun provideRepository(
        mindValleyRepository: MindValleyRepository
    ): IMindValleyRepository = mindValleyRepository

    @Singleton
    @Provides
    fun provideCoroutineDispatcher(
        dispatcherProvider: DefaultDispatcherProvider
    ): DispatcherProvider = dispatcherProvider
}