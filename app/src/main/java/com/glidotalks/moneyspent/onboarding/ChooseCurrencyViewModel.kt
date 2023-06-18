package com.glidotalks.moneyspent.onboarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChooseCurrencyViewModel @Inject constructor(
    private val usecase: SetCurrencyUsecase
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow(false)
    val uiStateFlow: StateFlow<Boolean> = _uiStateFlow

    private var _currencyList = mutableStateListOf<CurrencyItem>()
    val currencyList = _currencyList

    var uiState by mutableStateOf(ChooseCurrencyScreenUIState())
        private set

    private var currency: String? = null

    init {
        _currencyList.addAll(
            listOf(
                CurrencyItem(name = "Rupee", isChecked = false),
                CurrencyItem(name = "US Dollar", isChecked = false),
                CurrencyItem(name = "Euro", isChecked = false),
                CurrencyItem(name = "Australian Dollar", isChecked = false),
                CurrencyItem(name = "Cent", isChecked = false),
                CurrencyItem(name = "New Zealand Dollar", isChecked = false),
                CurrencyItem(name = "Singapore Dollar", isChecked = false),
                CurrencyItem(name = "Swiss Franc", isChecked = false),
                CurrencyItem(name = "British Pound", isChecked = false),
                CurrencyItem(name = "Omani Rial", isChecked = false),
                CurrencyItem(name = "Bahraini Dinar", isChecked = false)
            )
        )
    }


    fun handleFinishButtonClick() {
        viewModelScope.launch {
            usecase(currency ?: "Rupee")
            uiState = uiState.copy(isCurrencySaved = true)
        }
    }

    fun updateItem(index: Int, currency: CurrencyItem) {
        Timber.d("$index, $currency")
        this.currency = currency.name
        viewModelScope.launch {
            val prevIndex = withContext(Dispatchers.Default) {
                currencyList.forEachIndexed { index, currencyItem ->
                    if (currencyItem.isChecked) {
                        return@withContext index
                    }
                }

                return@withContext -1
            }
            if (prevIndex > -1) {
                currencyList[prevIndex] = currencyList[prevIndex].copy(isChecked = false)
            }
            currencyList[index] = currencyList[index].copy(isChecked = true)
            _uiStateFlow.value = true
        }
    }
}

data class CurrencyItem(
    val name: String,
    val isChecked: Boolean
)

data class ChooseCurrencyScreenUIState(
    val isCurrencySaved: Boolean = false
)