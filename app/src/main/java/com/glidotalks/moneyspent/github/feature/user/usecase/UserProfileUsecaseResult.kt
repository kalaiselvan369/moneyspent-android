package com.glidotalks.moneyspent.github.feature.user.usecase

import com.glidotalks.moneyspent.github.GithubConstants
import com.glidotalks.moneyspent.github.feature.user.model.UserResponse


sealed class UserProfileUsecaseResult {

    data class Success(val userResponse: UserResponse) : UserProfileUsecaseResult()
    data class Failure(
        val message: String = GithubConstants.ERROR_MESSAGE_USER_PROFILE_FETCH_FAILED
    ) : UserProfileUsecaseResult()
}