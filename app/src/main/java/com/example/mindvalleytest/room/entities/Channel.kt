package com.example.mindvalleytest.room.entities

import androidx.room.Entity

@Entity(primaryKeys = ["id", "title"])
data class Channel(
    val id: String = "",
    val title: String,
    val mediaCount: Int,
    val iconAssetUrl: String?,
    val coverAssetUrl: String?,
    val slug: String?
)