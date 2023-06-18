package com.glidotalks.android.template.common

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.BufferedSource
import okio.buffer
import okio.source
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection
import java.nio.charset.StandardCharsets

abstract class BaseMockWebServer {

    private lateinit var mockWebServer: MockWebServer

    protected fun <T> retrofitService(service: Class<T>): T = Retrofit.Builder()
        .baseUrl(mockWebServer.url(""))
        .addConverterFactory(MoshiConverterFactory.create(TestDependencies.moshi))
        .build()
        .create(service)

    protected fun startServer() {
        mockWebServer = MockWebServer()
    }

    protected fun stopServer() {
        mockWebServer.shutdown()
    }

    protected fun setDispatcher(dispatcher: Dispatcher) {
        mockWebServer.dispatcher = dispatcher
    }

    protected fun mockWebServer() = mockWebServer

    private fun getJsonAsBufferedSource(resourcePath: String): BufferedSource? {
        val inputStream = javaClass.classLoader?.getResourceAsStream(resourcePath)
        val source: BufferedSource? = inputStream?.let {
            inputStream.source().buffer()
        }
        return source
    }

    protected fun getMockResponse(jsonFilePath: String): MockResponse {
        return MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(getJsonAsBufferedSource(jsonFilePath)!!.readString(StandardCharsets.UTF_8))
    }
}