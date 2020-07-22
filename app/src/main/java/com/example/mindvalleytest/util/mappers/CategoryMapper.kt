package com.example.mindvalleytest.util.mappers

import com.example.mindvalleytest.network.models.CategoryResponse
import com.example.mindvalleytest.room.entities.Category
import com.example.mindvalleytest.util.Mapper
import javax.inject.Inject

class CategoryMapper @Inject constructor() : Mapper<CategoryResponse, List<Category>> {

    override suspend fun map(from: CategoryResponse): List<Category> {
        return from.categories.map {
            with(it) {
                Category(name)
            }
        }
    }
}