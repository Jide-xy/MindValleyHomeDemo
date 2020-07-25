package com.example.mindvalleytest.core.repository

import com.example.mindvalleytest.core.network.MindValleyService
import com.example.mindvalleytest.core.room.MindValleyDb
import com.example.mindvalleytest.core.room.entities.Category
import com.example.mindvalleytest.core.room.entities.NewEpisode
import com.example.mindvalleytest.core.room.models.ChannelsWithCoursesAndSeries
import com.example.mindvalleytest.core.util.DispatcherProvider
import com.example.mindvalleytest.core.util.NetworkStatus
import com.example.mindvalleytest.core.util.mappers.CategoryMapper
import com.example.mindvalleytest.core.util.mappers.ChannelMapper
import com.example.mindvalleytest.core.util.mappers.NewEpisodeMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MindValleyRepository @Inject constructor(
    private val localDb: MindValleyDb,
    private val remoteService: MindValleyService,
    private val channelMapper: ChannelMapper,
    private val newEpisodeMapper: NewEpisodeMapper,
    private val categoryMapper: CategoryMapper,
    dispatcherProvider: DispatcherProvider
) : BaseRepository(dispatcherProvider),
    IMindValleyRepository {

    override fun getChannels(): Flow<NetworkStatus<List<ChannelsWithCoursesAndSeries>>> {
        return bindLocalAndRemoteCalls(
            networkCall = {
                processNetworkResponse { remoteService.getChannels() }
            }, localDbCall = {
                localDb.channelsDao().getChannels()
            }, localDbObservableCall = {
                localDb.channelsDao().getChannelsAsFlow()
            }, saveNetworkResponse = {
                localDb.channelsDao().insertChannelsWithCoursesAndSeries(channelMapper.map(it))
            }
        )
    }

    override fun getNewEpisodes(): Flow<NetworkStatus<List<NewEpisode>>> {
        return bindLocalAndRemoteCalls(
            networkCall = {
                processNetworkResponse { remoteService.getNewEpisodes() }
            }, localDbCall = {
                localDb.newEpisodeDao().getNewEpisodes()
            }, localDbObservableCall = {
                localDb.newEpisodeDao().getNewEpisodesAsFlow()
            }, saveNetworkResponse = {
                localDb.newEpisodeDao().insertNewEpisodes(newEpisodeMapper.map(it))
            }
        )
    }

    override fun getCategories(): Flow<NetworkStatus<List<Category>>> {
        return bindLocalAndRemoteCalls(
            networkCall = {
                processNetworkResponse { remoteService.getCategories() }
            }, localDbCall = {
                localDb.categoriesDao().getCategories()
            }, localDbObservableCall = {
                localDb.categoriesDao().getCategoriesAsFlow()
            }, saveNetworkResponse = {
                localDb.categoriesDao().insertCategories(categoryMapper.map(it))
            }
        )
    }
}