package com.example.mindvalleytest.core.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(@PrimaryKey val name: String)