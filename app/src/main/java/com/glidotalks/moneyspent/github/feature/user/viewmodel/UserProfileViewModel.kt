package com.glidotalks.moneyspent.github.feature.user.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glidotalks.android.core.dispatchers.DispatcherProvider
import com.glidotalks.moneyspent.github.feature.user.usecase.UserProfileUsecase
import com.glidotalks.moneyspent.github.feature.user.usecase.UserProfileUsecaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val userProfileUsecase: UserProfileUsecase
) : ViewModel() {

    private var _userState: MutableStateFlow<String> = MutableStateFlow("loading")
    val userState = _userState.asStateFlow()

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch(dispatcherProvider.main) {
            userProfileUsecase(Unit).collect { usecaseResult ->
                when (usecaseResult) {
                    is UserProfileUsecaseResult.Failure -> {
                        Timber.d("usecase result failure")
                        _userState.value = "failure"
                    }

                    is UserProfileUsecaseResult.Success -> {
                        Timber.d("usecase result success")
                        _userState.value = "success"
                    }
                }
            }
        }
    }
}