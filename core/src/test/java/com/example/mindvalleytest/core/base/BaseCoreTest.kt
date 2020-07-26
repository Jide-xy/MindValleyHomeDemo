package com.example.mindvalleytest.core.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mindvalleytest.core.network.models.CategoryResponse
import com.example.mindvalleytest.core.network.models.ChannelsResponse
import com.example.mindvalleytest.core.network.models.DataResponse
import com.example.mindvalleytest.core.network.models.NewEpisodesResponse
import com.example.mindvalleytest.core.room.entities.Category
import com.example.mindvalleytest.core.room.entities.NewEpisode
import com.example.mindvalleytest.core.room.models.ChannelsWithCoursesAndSeries
import com.example.mindvalleytest.core.util.CoroutineTestRule
import com.example.mindvalleytest.core.util.mappers.CategoryMapper
import com.example.mindvalleytest.core.util.mappers.ChannelMapper
import com.example.mindvalleytest.core.util.mappers.NewEpisodeMapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okio.buffer
import okio.source
import org.junit.Rule
import java.lang.reflect.Type


@ExperimentalCoroutinesApi
abstract class BaseCoreTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    val channelMapper = ChannelMapper()
    val categoryMapper = CategoryMapper()
    val newEpisodeMapper = NewEpisodeMapper()

    fun <T> parseJsonFileToObject(fileName: String, type: Type): T? {
        val json = getJsonStringFromFile(fileName)
        val result: T = Gson().fromJson(json, type)
        return result
    }

    fun getJsonStringFromFile(fileName: String): String {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        val source = inputStream?.source()?.buffer()
        return source!!.readString(Charsets.UTF_8)
    }

    fun getMockChannels(): List<ChannelsWithCoursesAndSeries> = runBlocking {
        val type = object : TypeToken<DataResponse<ChannelsResponse>>() {}.type
        val channelsResponse = parseJsonFileToObject<DataResponse<ChannelsResponse>>(
            "sample-channel-response.json",
            type
        )!!.data
        return@runBlocking channelMapper.map(channelsResponse)
    }

    fun getMockNewEpisodes(): List<NewEpisode> = runBlocking {
        val type = object : TypeToken<DataResponse<NewEpisodesResponse>>() {}.type
        val newEpisodesResponse = parseJsonFileToObject<DataResponse<NewEpisodesResponse>>(
            "sample-new-episode-response.json",
            type
        )!!.data
        return@runBlocking newEpisodeMapper.map(newEpisodesResponse)
    }

    fun getMockCategories(): List<Category> = runBlocking {
        val type = object : TypeToken<DataResponse<CategoryResponse>>() {}.type
        val categoriesResponse = parseJsonFileToObject<DataResponse<CategoryResponse>>(
            "sample-categories-response.json",
            type
        )!!.data
        return@runBlocking categoryMapper.map(categoriesResponse)
    }
}