package com.example.mindvalleytest.core.util.mappers

import javax.inject.Inject

class NewEpisodeMapper @Inject constructor() :
    com.example.mindvalleytest.core.util.Mapper<com.example.mindvalleytest.core.network.models.NewEpisodesResponse, List<com.example.mindvalleytest.core.room.entities.NewEpisode>> {
    override suspend fun map(from: com.example.mindvalleytest.core.network.models.NewEpisodesResponse): List<com.example.mindvalleytest.core.room.entities.NewEpisode> {
        return from.media.map {
            with(it) {
                com.example.mindvalleytest.core.room.entities.NewEpisode(
                    type, title, coverAsset.url, channel.title
                )
            }
        }
    }
}