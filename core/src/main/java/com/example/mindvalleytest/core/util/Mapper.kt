package com.example.mindvalleytest.core.util

interface Mapper<F, T> {

    /**
     * Mapping object.
     *
     * @param from Initial object to from mapping.
     * @return An Object containing the results of applying the transformation.
     */
    suspend fun map(from: F): T
}