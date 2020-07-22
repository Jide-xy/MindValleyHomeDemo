package com.example.mindvalleytest.network.models

data class ChannelsResponse(val channels: List<ApiChannels>)

data class ApiChannels(
    val title: String,
    val mediaCount: Int,
    val series: List<ApiSeries>,
    val latestMedia: List<ApiCourses>,
    val id: String?,
    val iconAsset: IconAsset?,
    val coverAsset: CoverAsset,
    val slug: String?
)

data class ApiSeries(
    val id: String?,
    val title: String,
    val coverAsset: CoverAsset
)

data class ApiCourses(
    val type: String,
    val title: String,
    val coverAsset: CoverAsset
)

data class IconAsset(
    val thumbnailUrl: String?,
    val url: String?
)