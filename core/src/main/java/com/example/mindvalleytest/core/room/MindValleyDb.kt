package com.example.mindvalleytest.core.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mindvalleytest.core.room.dao.CategoryDao
import com.example.mindvalleytest.core.room.dao.ChannelDao
import com.example.mindvalleytest.core.room.dao.NewEpisodeDao
import com.example.mindvalleytest.core.room.entities.*

const val name = "MIND_VALLEY_DB"

@Database(
    entities = [Category::class, Course::class, Channel::class, NewEpisode::class, Series::class],
    version = 1
)
abstract class MindValleyDb : RoomDatabase() {

    abstract fun channelsDao(): ChannelDao
    abstract fun categoriesDao(): CategoryDao
    abstract fun newEpisodeDao(): NewEpisodeDao

    companion object {

        @Volatile
        private var INSTANCE: MindValleyDb? = null

        fun getInstance(context: Context): MindValleyDb =
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: buildDatabase(
                            context
                        )
                            .also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MindValleyDb::class.java,
                name
            ).build()

    }
}