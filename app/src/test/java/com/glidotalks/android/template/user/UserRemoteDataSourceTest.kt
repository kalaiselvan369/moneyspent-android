package com.glidotalks.android.template.user

import com.glidotalks.android.core.api.ApiResult
import com.glidotalks.android.template.TestConstants
import com.glidotalks.android.template.common.BaseMockWebServer
import com.glidotalks.android.template.common.TestDependencies
import com.glidotalks.android.template.common.TestDependencies.getJson
import com.glidotalks.moneyspent.github.api.GithubApiService
import com.glidotalks.moneyspent.github.feature.user.datasource.UserRemoteDatasource
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class UserRemoteDataSourceTest : BaseMockWebServer() {

    private lateinit var githubApiService: GithubApiService
    private lateinit var userDataSource: UserRemoteDatasource

    @Before
    fun setUp() {
        startServer()
        githubApiService = retrofitService(GithubApiService::class.java)
        userDataSource = UserRemoteDatasource(TestDependencies.moshi, githubApiService)
        setDispatcher(UserServiceDispatcher())
    }

    @Test
    fun `test get user success`() {
        runBlocking {
            when(userDataSource.getUser()) {
                is ApiResult.Failure -> assert(false)
                is ApiResult.Success -> assert(true)
            }
        }
    }

    @After
    fun tearDown() {
        stopServer()
    }

    class UserServiceDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                TestConstants.TEST_API_GET_USER -> {
                    MockResponse()
                        .setResponseCode(HttpURLConnection.HTTP_OK)
                        .setBody(getJson(TestConstants.GET_USER_HTTP_200_JSON_FILE_PATH))
                }

                else -> {
                    MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
                }
            }
        }
    }
}