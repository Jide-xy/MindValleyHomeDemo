package com.example.mindvalleytest.util.mappers

import com.example.mindvalleytest.network.models.NewEpisodesResponse
import com.example.mindvalleytest.room.entities.NewEpisode
import com.example.mindvalleytest.util.Mapper
import javax.inject.Inject

class NewEpisodeMapper @Inject constructor() : Mapper<NewEpisodesResponse, List<NewEpisode>> {
    override suspend fun map(from: NewEpisodesResponse): List<NewEpisode> {
        return from.media.map {
            with(it) {
                NewEpisode(
                    type, title, coverAsset.url, channel.title
                )
            }
        }
    }
}