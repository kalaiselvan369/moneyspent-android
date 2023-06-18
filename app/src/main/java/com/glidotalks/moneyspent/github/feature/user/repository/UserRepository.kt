package com.glidotalks.moneyspent.github.feature.user.repository

import com.glidotalks.android.core.api.ApiResult
import com.glidotalks.moneyspent.github.feature.user.model.UserResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUser(): Flow<ApiResult<UserResponse>>
}