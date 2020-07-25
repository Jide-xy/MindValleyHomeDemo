package com.example.mindvalleytest.core.util.mappers

import javax.inject.Inject

class CategoryMapper @Inject constructor() :
    com.example.mindvalleytest.core.util.Mapper<com.example.mindvalleytest.core.network.models.CategoryResponse, List<com.example.mindvalleytest.core.room.entities.Category>> {

    override suspend fun map(from: com.example.mindvalleytest.core.network.models.CategoryResponse): List<com.example.mindvalleytest.core.room.entities.Category> {
        return from.categories.map {
            with(it) {
                com.example.mindvalleytest.core.room.entities.Category(name)
            }
        }
    }
}