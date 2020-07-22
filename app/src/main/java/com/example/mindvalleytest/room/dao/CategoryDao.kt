package com.example.mindvalleytest.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mindvalleytest.room.entities.Category
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CategoryDao {

    @Query("SELECT * FROM category")
    abstract fun getCategoriesAsFlow(): Flow<List<Category>>

    @Query("SELECT * FROM category")
    abstract suspend fun getCategories(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCategories(categories: List<Category>)
}