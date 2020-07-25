package com.example.mindvalleytest.core.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class NewEpisode(
    val type: String,
    @PrimaryKey val title: String,
    val url: String,
    val channelTitle: String
)