package com.glidotalks.moneyspent.github.feature.user.usecase


import com.glidotalks.android.core.api.ApiResult
import com.glidotalks.android.core.dispatchers.DispatcherProvider
import com.glidotalks.android.core.usecase.FlowUsecase
import com.glidotalks.moneyspent.github.feature.user.model.UserResponse
import com.glidotalks.moneyspent.github.feature.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

class UserProfileUsecase(
    dispatcherProvider: DispatcherProvider,
    private val userRepository: UserRepository
) : FlowUsecase<Unit, UserProfileUsecaseResult>(dispatcherProvider.io) {
    override suspend fun execute(param: Unit): Flow<UserProfileUsecaseResult> {
        return flow {
            userRepository.getUser().collect { apiResult ->
                val usecaseResult = getUsecaseResult(apiResult)
                emit(usecaseResult)
            }
        }
    }

    override suspend fun exception(flowCollector: FlowCollector<UserProfileUsecaseResult>) {
        flowCollector.emit(UserProfileUsecaseResult.Failure())
    }

    private fun getUsecaseResult(apiResult: ApiResult<UserResponse>): UserProfileUsecaseResult {
        return when (apiResult) {
            is ApiResult.Failure -> UserProfileUsecaseResult.Failure()
            is ApiResult.Success -> handleApiSuccess(apiResult.result)
        }
    }

    private fun handleApiSuccess(userResponse: UserResponse?): UserProfileUsecaseResult {
        return when (userResponse) {
            null -> UserProfileUsecaseResult.Failure()
            else -> UserProfileUsecaseResult.Success(userResponse)
        }
    }
}