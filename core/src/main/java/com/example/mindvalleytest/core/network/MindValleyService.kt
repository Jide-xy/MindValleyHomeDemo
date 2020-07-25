package com.example.mindvalleytest.core.network

import com.example.mindvalleytest.core.network.models.CategoryResponse
import com.example.mindvalleytest.core.network.models.ChannelsResponse
import com.example.mindvalleytest.core.network.models.DataResponse
import com.example.mindvalleytest.core.network.models.NewEpisodesResponse
import retrofit2.Response
import retrofit2.http.GET

interface MindValleyService {

    @GET("raw/A0CgArX3")
    suspend fun getCategories(): Response<DataResponse<CategoryResponse>>

    @GET("raw/z5AExTtw")
    suspend fun getNewEpisodes(): Response<DataResponse<NewEpisodesResponse>>

    @GET("raw/Xt12uVhM")
    suspend fun getChannels(): Response<DataResponse<ChannelsResponse>>
}