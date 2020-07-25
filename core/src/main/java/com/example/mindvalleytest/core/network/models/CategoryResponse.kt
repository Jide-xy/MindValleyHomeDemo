package com.example.mindvalleytest.core.network.models

data class CategoryResponse(
    val categories: List<ApiCategory>
)

data class ApiCategory(
    val name: String
)