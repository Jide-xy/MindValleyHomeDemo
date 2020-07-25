package com.example.mindvalleytest.core.room.dao

import androidx.room.*
import com.example.mindvalleytest.core.room.entities.Channel
import com.example.mindvalleytest.core.room.entities.Course
import com.example.mindvalleytest.core.room.entities.Series
import com.example.mindvalleytest.core.room.models.ChannelsWithCoursesAndSeries
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ChannelDao {

    @Query("SELECT * FROM channel")
    abstract fun getChannelsAsFlow(): Flow<List<ChannelsWithCoursesAndSeries>>

    @Query("SELECT * FROM channel")
    abstract suspend fun getChannels(): List<ChannelsWithCoursesAndSeries>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertChannel(channel: Channel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertSeries(series: List<Series>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCourses(courses: List<Course>)

    @Query("Delete from channel where title = :title and id = :id")
    abstract suspend fun deleteChannel(id: String, title: String)

    @Transaction
    open suspend fun insertChannelsWithCoursesAndSeries(channels: List<ChannelsWithCoursesAndSeries>) {
        for (channel in channels) {
            deleteChannel(channel.channel.id, channel.channel.title)
            insertChannel(channel.channel)
            insertCourses(channel.courses)
            insertSeries(channel.series)
        }
    }
}