package com.glidotalks.moneyspent.onboarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor() : ViewModel() {

    private val _uiStateFlow = MutableStateFlow(false)
    val uiStateFlow: StateFlow<Boolean> = _uiStateFlow

    private val _userInputFlow = MutableStateFlow("")
    val userInputFlow: StateFlow<String> = _userInputFlow

    var uiState by mutableStateOf(WelcomeScreenUIState())
        private set


    fun handleNextButtonClick() {
        viewModelScope.launch {
            uiState = uiState.copy(isDisclaimerRead = true)
        }
    }
}

data class WelcomeScreenUIState(
    val isDisclaimerRead: Boolean = false
)
