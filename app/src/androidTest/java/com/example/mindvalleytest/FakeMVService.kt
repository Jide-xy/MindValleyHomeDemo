package com.example.mindvalleytest

import com.example.mindvalleytest.core.network.MindValleyService
import com.example.mindvalleytest.core.network.models.CategoryResponse
import com.example.mindvalleytest.core.network.models.ChannelsResponse
import com.example.mindvalleytest.core.network.models.DataResponse
import com.example.mindvalleytest.core.network.models.NewEpisodesResponse
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import okio.Okio
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FakeMVService : MindValleyService {

    val mockWebServer: MockWebServer = MockWebServer()
    private val service = Retrofit.Builder()
        .baseUrl(mockWebServer.url(""))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MindValleyService::class.java)

    override suspend fun getCategories(): Response<DataResponse<CategoryResponse>> {
        enqueueResponse("sample-categories-response.json")
        return service.getCategories()
    }

    override suspend fun getNewEpisodes(): Response<DataResponse<NewEpisodesResponse>> {
        enqueueResponse("sample-new-episode-response.json")
        return service.getNewEpisodes()
    }

    override suspend fun getChannels(): Response<DataResponse<ChannelsResponse>> {
        enqueueResponse("sample-channel-response.json")
        return service.getChannels()
    }

    fun shutDown() {
        mockWebServer.shutdown()
    }

    private fun enqueueResponse(
        fileName: String,
        headers: Map<String, String> = emptyMap(),
        socketPolicy: SocketPolicy = SocketPolicy.KEEP_OPEN
    ) {
        val mockResponse = MockResponse().apply {
            this.socketPolicy = socketPolicy
            for ((key, value) in headers) {
                addHeader(key, value)
            }
        }

        mockWebServer.enqueue(
            mockResponse
                .setBody(getJsonStringFromFile(fileName))
        )
    }

    fun getJsonStringFromFile(fileName: String): String {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        val source = Okio.buffer(Okio.source(inputStream!!))
        return source!!.readString(Charsets.UTF_8)
    }
}