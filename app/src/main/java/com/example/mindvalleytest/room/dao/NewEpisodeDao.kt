package com.example.mindvalleytest.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mindvalleytest.room.entities.NewEpisode
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NewEpisodeDao {

    @Query("SELECT * FROM newepisode")
    abstract fun getNewEpisodesAsFlow(): Flow<List<NewEpisode>>

    @Query("SELECT * FROM newepisode")
    abstract suspend fun getNewEpisodes(): List<NewEpisode>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertNewEpisodes(newEpisodes: List<NewEpisode>)
}