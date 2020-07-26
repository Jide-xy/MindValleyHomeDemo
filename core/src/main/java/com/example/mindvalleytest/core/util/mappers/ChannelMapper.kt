package com.example.mindvalleytest.core.util.mappers

import com.example.mindvalleytest.core.network.models.ChannelsResponse
import com.example.mindvalleytest.core.room.entities.Channel
import com.example.mindvalleytest.core.room.entities.Course
import com.example.mindvalleytest.core.room.entities.Series
import com.example.mindvalleytest.core.room.models.ChannelsWithCoursesAndSeries
import com.example.mindvalleytest.core.util.Mapper
import javax.inject.Inject

class ChannelMapper @Inject constructor() :
    Mapper<ChannelsResponse, List<ChannelsWithCoursesAndSeries>> {
    override suspend fun map(from: ChannelsResponse): List<ChannelsWithCoursesAndSeries> {
        return from.channels.map {
            with(it) {
                ChannelsWithCoursesAndSeries(
                    Channel(
                        id.orEmpty(),
                        title,
                        mediaCount,
                        iconAsset?.thumbnailUrl ?: iconAsset?.url,
                        coverAsset.url, slug
                    ),
                    this.series.map { apiSeries ->
                        Series(
                            apiSeries.title,
                            apiSeries.coverAsset.url,
                            id.orEmpty(),
                            title
                        )
                    },
                    this.latestMedia.map { course ->
                        Course(
                            course.type,
                            course.title,
                            course.coverAsset.url,
                            id.orEmpty(),
                            title
                        )
                    }
                )

            }
        }
    }
}