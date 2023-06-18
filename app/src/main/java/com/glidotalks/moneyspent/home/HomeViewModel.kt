package com.glidotalks.moneyspent.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeScreenEntryUsecase: HomeScreenEntryUsecase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeScreenUIState>(HomeScreenUIState.InProgress)
    val uiState: StateFlow<HomeScreenUIState>
        get() = _uiState

    fun getUserSession() {
        Timber.d("called")
        viewModelScope.launch {
            homeScreenEntryUsecase(Unit).collectLatest { usecaseResult ->
                when (usecaseResult) {
                    HomeScreenEntryUsecaseResult.Failure -> {
                        Timber.d("usecase error")
                        setState(false)
                    }

                    HomeScreenEntryUsecaseResult.HomeEntryGranted -> {
                        Timber.d("home entry successful")
                        setState(true)
                    }

                    HomeScreenEntryUsecaseResult.OnboardingNotCompleted -> {
                        Timber.d("onboarding not completed")
                        setState(false)
                    }
                }
            }
        }
    }

    private suspend fun setState(result: Boolean) {
        _uiState.emit(if (result) HomeScreenUIState.AllConditionsMet else HomeScreenUIState.OnboardingMissing)
    }
}

sealed class HomeScreenUIState {
    object InProgress : HomeScreenUIState()
    object OnboardingMissing : HomeScreenUIState()
    object AllConditionsMet : HomeScreenUIState()
}