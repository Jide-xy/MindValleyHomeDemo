package com.example.mindvalleytest.core.network

import androidx.annotation.CallSuper
import com.example.mindvalleytest.core.base.BaseCoreTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
abstract class NetworkTestSetup : BaseCoreTest() {

    lateinit var service: MindValleyService
    lateinit var mockWebServer: MockWebServer

    @CallSuper
    @Before
    open fun setUp() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MindValleyService::class.java)
    }

    @CallSuper
    @After
    open fun tearDown() {
        mockWebServer.shutdown()
    }

    protected fun enqueueResponse(
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
}