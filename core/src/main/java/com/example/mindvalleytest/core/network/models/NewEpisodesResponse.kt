package com.example.mindvalleytest.core.network.models

data class ApiNewEpisodes(
    val type: String,
    val title: String,
    val coverAsset: CoverAsset,
    val channel: NewEpisodeChannel
)

data class NewEpisodesResponse(val media: List<ApiNewEpisodes>)

data class CoverAsset(val url: String)

data class NewEpisodeChannel(val title: String)