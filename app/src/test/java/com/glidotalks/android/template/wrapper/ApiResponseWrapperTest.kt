package com.glidotalks.android.template.wrapper

import MockGithubApiResponse
import com.glidotalks.android.core.api.ApiResponseWrapper
import com.glidotalks.android.core.api.ApiResult
import com.glidotalks.android.template.TestConstants
import com.glidotalks.android.template.common.BaseMockWebServer
import com.glidotalks.android.template.common.TestDependencies
import com.glidotalks.moneyspent.github.api.GithubApiService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ApiResponseWrapperTest : BaseMockWebServer() {

    private lateinit var apiResponseWrapper: ApiResponseWrapper
    private lateinit var githubApiService: GithubApiService

    @Before
    fun setUp() {
        startServer()
        githubApiService = retrofitService(GithubApiService::class.java)
        apiResponseWrapper = ApiResponseWrapper(TestDependencies.moshi)
    }

    @Test
    fun `safe api call success test`() {
        runBlocking {
            mockWebServer().enqueue(
                getMockResponse(TestConstants.GET_USER_HTTP_200_JSON_FILE_PATH)
            )
            val apiResult = apiResponseWrapper.safeApiCall {
                githubApiService.user()
            }
            mockWebServer().takeRequest()
            when (apiResult) {
                is ApiResult.Success -> {
                    assertThat(apiResult.result?.id).isEqualTo(14306248)
                }
                else -> assert(false)
            }
        }
    }

    @Test
    fun `test session unauthorized`() {
        runTest {
            val apiResult = apiResponseWrapper.safeApiCall {
                MockGithubApiResponse.mockUnauthorizedResponse()
            }
            when (apiResult) {
                is ApiResult.Success -> {
                    assert(false)
                }
                is ApiResult.Failure ->  assert(true)
            }
        }
    }


    @After
    fun tearDown() {
        stopServer()
    }
}