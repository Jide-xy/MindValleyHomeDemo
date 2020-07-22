package com.example.mindvalleytest.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Channel::class,
        parentColumns = ["id", "title"],
        childColumns = ["channelId", "channelTitle"],
        onDelete = ForeignKey.CASCADE
    )]
)

data class Course(
    val type: String,
    val title: String,
    val url: String,
    val channelId: String,
    val channelTitle: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)