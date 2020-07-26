package com.example.mindvalleytest.core.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mindvalleytest.core.network.NetworkTestSetup
import com.example.mindvalleytest.core.room.MindValleyDb
import com.example.mindvalleytest.core.util.NetworkStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.SocketPolicy
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MindValleyRepositoryTest : NetworkTestSetup() {


    private lateinit var repository: IMindValleyRepository
    private lateinit var db: MindValleyDb

    override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, MindValleyDb::class.java)
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            .build()
        repository = MindValleyRepository(
            db,
            service,
            channelMapper,
            newEpisodeMapper,
            categoryMapper,
            coroutinesTestRule.testDispatcherProvider
        )
    }

    @Throws(IOException::class)
    override fun tearDown() {
        super.tearDown()
        db.close()
    }

    @Test
    fun `verify getChannels when local db is empty then call api successfully`() = runBlocking {
        enqueueResponse("sample-channel-response.json")
        val flow = repository.getChannels()
        val items = flow.take(2).toList()
        var loading = items[0]
        Assert.assertThat(loading, instanceOf(NetworkStatus.Loading::class.java))
        loading = loading as NetworkStatus.Loading
        Assert.assertThat(loading.data, `is`(emptyList()))
        val success = items[1]
        Assert.assertThat(success, instanceOf(NetworkStatus.Success::class.java))
        val channels = (success as NetworkStatus.Success).data
        Assert.assertThat(channels.size, `is`(9))
    }

    @Test
    fun `verify getChannels when local db is NOT empty then call api successfully`() = runBlocking {
        enqueueResponse("sample-channel-response.json")
        val cachedChannels = getMockChannels().take(5)
        db.channelsDao().insertChannelsWithCoursesAndSeries(cachedChannels)
        val flow = repository.getChannels()
        val items = flow.take(2).toList()
        var loading = items[0]
        Assert.assertThat(loading, instanceOf(NetworkStatus.Loading::class.java))
        loading = loading as NetworkStatus.Loading
        Assert.assertThat(loading.data!!.size, `is`(cachedChannels.size))
        val success = items[1]
        Assert.assertThat(success, instanceOf(NetworkStatus.Success::class.java))
        val channels = (success as NetworkStatus.Success).data
        Assert.assertThat(channels.size, `is`(9))
    }

    @Test
    fun `verify getChannels when local db is empty then call api fails`() = runBlocking {
        enqueueResponse(
            "sample-channel-response.json",
            socketPolicy = SocketPolicy.DISCONNECT_AFTER_REQUEST
        )
        val flow = repository.getChannels()
        val items = flow.take(2).toList()
        var loading = items[0]
        Assert.assertThat(loading, instanceOf(NetworkStatus.Loading::class.java))
        loading = loading as NetworkStatus.Loading
        Assert.assertThat(loading.data, `is`(emptyList()))
        val error = items[1]
        Assert.assertThat(error, instanceOf(NetworkStatus.Error::class.java))
        val channels = (error as NetworkStatus.Error).data
        Assert.assertThat(channels!!.size, `is`(0))
    }

    @Test
    fun `verify getNewEpisodes when local db is empty then call api successfully`() = runBlocking {
        enqueueResponse("sample-new-episode-response.json")
        val flow = repository.getNewEpisodes()
        val items = flow.take(2).toList()
        var loading = items[0]
        Assert.assertThat(loading, instanceOf(NetworkStatus.Loading::class.java))
        loading = loading as NetworkStatus.Loading
        Assert.assertThat(loading.data, `is`(emptyList()))
        val success = items[1]
        Assert.assertThat(success, instanceOf(NetworkStatus.Success::class.java))
        val newEpisodes = (success as NetworkStatus.Success).data
        Assert.assertThat(newEpisodes.size, `is`(6))
    }

    @Test
    fun `verify getNewEpisodes when local db is NOT empty then call api successfully`() =
        runBlocking {
            enqueueResponse("sample-new-episode-response.json")
            val cachedEpisodes = getMockNewEpisodes().take(3)
            db.newEpisodeDao().insertNewEpisodes(cachedEpisodes)
            val flow = repository.getNewEpisodes()
            val items = flow.take(2).toList()
            var loading = items[0]
            Assert.assertThat(loading, instanceOf(NetworkStatus.Loading::class.java))
            loading = loading as NetworkStatus.Loading
            Assert.assertThat(loading.data!!.size, `is`(cachedEpisodes.size))
            val success = items[1]
            Assert.assertThat(success, instanceOf(NetworkStatus.Success::class.java))
            val newEpisodes = (success as NetworkStatus.Success).data
            Assert.assertThat(newEpisodes.size, `is`(6))
        }

    @Test
    fun `verify getNewEpisodes when local db is empty then call api fails`() = runBlocking {
        enqueueResponse(
            "sample-new-episode-response.json",
            socketPolicy = SocketPolicy.DISCONNECT_AFTER_REQUEST
        )
        val flow = repository.getNewEpisodes()
        val items = flow.take(2).toList()
        var loading = items[0]
        Assert.assertThat(loading, instanceOf(NetworkStatus.Loading::class.java))
        loading = loading as NetworkStatus.Loading
        Assert.assertThat(loading.data, `is`(emptyList()))
        val error = items[1]
        Assert.assertThat(error, instanceOf(NetworkStatus.Error::class.java))
        val newEpisodes = (error as NetworkStatus.Error).data
        Assert.assertThat(newEpisodes!!.size, `is`(0))
    }

    @Test
    fun `verify getCategories when local db is empty then call api successfully`() = runBlocking {
        enqueueResponse("sample-categories-response.json")
        val flow = repository.getCategories()
        val items = flow.take(2).toList()
        var loading = items[0]
        Assert.assertThat(loading, instanceOf(NetworkStatus.Loading::class.java))
        loading = loading as NetworkStatus.Loading
        Assert.assertThat(loading.data, `is`(emptyList()))
        val success = items[1]
        Assert.assertThat(success, instanceOf(NetworkStatus.Success::class.java))
        val categories = (success as NetworkStatus.Success).data
        Assert.assertThat(categories.size, `is`(12))
    }

    @Test
    fun `verify getCategories when local db is NOT empty then call api successfully`() =
        runBlocking {
            enqueueResponse("sample-categories-response.json")
            val cachedCategories = getMockCategories()
            db.categoriesDao().insertCategories(cachedCategories)
            val flow = repository.getCategories()
            val items = flow.take(2).toList()
            var loading = items[0]
            Assert.assertThat(loading, instanceOf(NetworkStatus.Loading::class.java))
            loading = loading as NetworkStatus.Loading
            Assert.assertThat(loading.data!!.size, `is`(cachedCategories.size))
            val success = items[1]
            Assert.assertThat(success, instanceOf(NetworkStatus.Success::class.java))
            val categories = (success as NetworkStatus.Success).data
            Assert.assertThat(categories.size, `is`(12))
        }

    @Test
    fun `verify getCategories when local db is empty then call api fails`() = runBlocking {
        enqueueResponse(
            "sample-categories-response.json",
            socketPolicy = SocketPolicy.DISCONNECT_AFTER_REQUEST
        )
        val flow = repository.getCategories()
        val items = flow.take(2).toList()
        var loading = items[0]
        Assert.assertThat(loading, instanceOf(NetworkStatus.Loading::class.java))
        loading = loading as NetworkStatus.Loading
        Assert.assertThat(loading.data, `is`(emptyList()))
        val error = items[1]
        Assert.assertThat(error, instanceOf(NetworkStatus.Error::class.java))
        val categories = (error as NetworkStatus.Error).data
        Assert.assertThat(categories!!.size, `is`(0))
    }
}