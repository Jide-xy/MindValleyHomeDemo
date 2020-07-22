package com.example.mindvalleytest.repository

import com.example.mindvalleytest.room.entities.Category
import com.example.mindvalleytest.room.entities.NewEpisode
import com.example.mindvalleytest.room.models.ChannelsWithCoursesAndSeries
import com.example.mindvalleytest.util.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface IMindValleyRepository {
    fun getChannels(): Flow<NetworkStatus<List<ChannelsWithCoursesAndSeries>>>
    fun getNewEpisodes(): Flow<NetworkStatus<List<NewEpisode>>>
    fun getCategories(): Flow<NetworkStatus<List<Category>>>
}