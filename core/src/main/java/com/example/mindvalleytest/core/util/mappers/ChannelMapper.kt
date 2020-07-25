package com.example.mindvalleytest.core.util.mappers

import javax.inject.Inject

class ChannelMapper @Inject constructor() :
    com.example.mindvalleytest.core.util.Mapper<com.example.mindvalleytest.core.network.models.ChannelsResponse, List<com.example.mindvalleytest.core.room.models.ChannelsWithCoursesAndSeries>> {
    override suspend fun map(from: com.example.mindvalleytest.core.network.models.ChannelsResponse): List<com.example.mindvalleytest.core.room.models.ChannelsWithCoursesAndSeries> {
        return from.channels.map {
            with(it) {
                com.example.mindvalleytest.core.room.models.ChannelsWithCoursesAndSeries(
                    com.example.mindvalleytest.core.room.entities.Channel(
                        id.orEmpty(),
                        title,
                        mediaCount,
                        iconAsset?.thumbnailUrl ?: iconAsset?.url,
                        coverAsset.url, slug
                    ),
                    this.series.map { apiSeries ->
                        com.example.mindvalleytest.core.room.entities.Series(
                            apiSeries.title,
                            apiSeries.coverAsset.url,
                            id.orEmpty(),
                            title
                        )
                    },
                    this.latestMedia.map { course ->
                        com.example.mindvalleytest.core.room.entities.Course(
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