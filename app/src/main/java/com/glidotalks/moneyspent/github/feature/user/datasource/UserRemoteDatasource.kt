package com.glidotalks.moneyspent.github.feature.user.datasource


import com.glidotalks.android.core.api.ApiResponseWrapper
import com.glidotalks.android.core.api.ApiResult
import com.glidotalks.moneyspent.github.api.GithubApiService
import com.glidotalks.moneyspent.github.feature.user.model.UserResponse
import com.squareup.moshi.Moshi

class UserRemoteDatasource(
    moshi: Moshi,
    private val apiService: GithubApiService
) : ApiResponseWrapper(moshi) {

    suspend fun getUser(): ApiResult<UserResponse> {
        return safeApiCall {
            apiService.user()
        }
    }
}