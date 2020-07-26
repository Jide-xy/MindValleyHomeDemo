package com.example.mindvalleytest.core.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mindvalleytest.core.base.BaseCoreTest
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.Executors

@RunWith(AndroidJUnit4::class)
class MindValleyDbTest : BaseCoreTest() {

    private lateinit var db: MindValleyDb

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, MindValleyDb::class.java)
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun `confirm channels insertion and read`() = runBlocking {
        val channels = getMockChannels()
        db.channelsDao().insertChannelsWithCoursesAndSeries(channels)
        val dbChannels = db.channelsDao().getChannels()
        Assert.assertEquals(channels.size, dbChannels.size)
        Assert.assertThat(channels.first().channel, `is`(dbChannels.first().channel))
    }

    @Test
    @Throws(Exception::class)
    fun `confirm channels deletion`() = runBlocking {
        val channels = getMockChannels()
        db.channelsDao().insertChannelsWithCoursesAndSeries(channels)
        db.channelsDao().deleteChannel(channels.first().channel.id, channels.first().channel.title)
        val dbChannels = db.channelsDao().getChannels()
        Assert.assertEquals(channels.size - 1, dbChannels.size)
        Assert.assertThat(dbChannels, not(hasItem(channels.first())))
    }

    @Test
    @Throws(Exception::class)
    fun `confirm new episodes insertion and read`() = runBlocking {
        val newEpisodes = getMockNewEpisodes()
        db.newEpisodeDao().insertNewEpisodes(newEpisodes)
        val dbNewEpisodes = db.newEpisodeDao().getNewEpisodes()
        Assert.assertEquals(newEpisodes.size, dbNewEpisodes.size)
        Assert.assertThat(
            newEpisodes.first().channelTitle,
            `is`(dbNewEpisodes.first().channelTitle)
        )
    }

    @Test
    @Throws(Exception::class)
    fun `confirm categories insertion and read`() = runBlocking {
        val mockCategories = getMockCategories()
        db.categoriesDao().insertCategories(mockCategories)
        val dbCategories = db.categoriesDao().getCategories()
        Assert.assertEquals(mockCategories.size, dbCategories.size)
        Assert.assertThat(mockCategories.first().name, `is`(dbCategories.first().name))
    }

}