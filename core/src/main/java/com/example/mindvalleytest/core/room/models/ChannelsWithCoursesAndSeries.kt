package com.example.mindvalleytest.core.room.models

import androidx.room.Embedded
import androidx.room.Relation
import com.example.mindvalleytest.core.room.entities.Channel
import com.example.mindvalleytest.core.room.entities.Course
import com.example.mindvalleytest.core.room.entities.Series

data class ChannelsWithCoursesAndSeries(
    @Embedded
    val channel: Channel,
    @Relation(parentColumn = "title", entityColumn = "channelTitle")
    val series: List<Series>,
    @Relation(parentColumn = "title", entityColumn = "channelTitle")
    val courses: List<Course>
) {
    fun isSeries() = series.isEmpty().not()
}