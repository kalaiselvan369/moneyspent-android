package com.glidotalks.moneyspent.github.feature.user.repository

import com.glidotalks.android.core.api.ApiResult
import com.glidotalks.moneyspent.github.feature.user.datasource.UserLocalDatasource
import com.glidotalks.moneyspent.github.feature.user.datasource.UserRemoteDatasource
import com.glidotalks.moneyspent.github.feature.user.model.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDatasource: UserRemoteDatasource,
    private val localDatasource: UserLocalDatasource
) : UserRepository {

    override suspend fun getUser(): Flow<ApiResult<UserResponse>> {
        return flow {
            emit(remoteDatasource.getUser())
        }
    }
}