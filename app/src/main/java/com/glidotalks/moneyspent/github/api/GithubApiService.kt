package com.glidotalks.moneyspent.github.api

import com.glidotalks.moneyspent.github.feature.user.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface GithubApiService {

    @GET("user")
    suspend fun user(): Response<UserResponse>
}