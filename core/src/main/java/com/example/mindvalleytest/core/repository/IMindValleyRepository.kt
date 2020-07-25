package com.example.mindvalleytest.core.repository

import com.example.mindvalleytest.core.room.entities.Category
import com.example.mindvalleytest.core.room.entities.NewEpisode
import com.example.mindvalleytest.core.room.models.ChannelsWithCoursesAndSeries
import com.example.mindvalleytest.core.util.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface IMindValleyRepository {
    fun getChannels(): Flow<NetworkStatus<List<ChannelsWithCoursesAndSeries>>>
    fun getNewEpisodes(): Flow<NetworkStatus<List<NewEpisode>>>
    fun getCategories(): Flow<NetworkStatus<List<Category>>>
}