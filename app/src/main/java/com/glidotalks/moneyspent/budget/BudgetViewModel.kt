package com.glidotalks.moneyspent.budget

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glidotalks.android.core.db.budget.Budget
import com.glidotalks.android.core.dispatchers.DispatcherProvider
import com.glidotalks.android.core.usecase.budget.BudgetUsecase
import com.glidotalks.android.core.usecase.budget.BudgetUsecaseResult
import com.glidotalks.android.core.usecase.budget.SetBudgetUsecase
import com.glidotalks.android.core.usecase.budget.SetBudgetUsecaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val budgetUsecase: BudgetUsecase,
    private val setBudgetUsecase: SetBudgetUsecase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _budgetStateFlow: MutableStateFlow<BudgetUIState> =
        MutableStateFlow(BudgetUIState.Loading)
    val budgetStateFlow: StateFlow<BudgetUIState> = _budgetStateFlow

    var budgetInputStateFlow = mutableStateOf<String>("")

    var buttonState = mutableStateOf<Boolean>(false)

    fun getCurrentBudget() {
        viewModelScope.launch(dispatcherProvider.main) {
            budgetUsecase(Unit).collectLatest { usecaseResult ->
                when (usecaseResult) {
                    BudgetUsecaseResult.Empty -> {
                        Timber.d("empty")
                    }

                    BudgetUsecaseResult.Failure -> {
                        Timber.d("failure")
                    }

                    is BudgetUsecaseResult.Success -> {
                        Timber.d("success")
                        _budgetStateFlow.emit(value = BudgetUIState.ExistingBudget(usecaseResult.budget))
                        buttonState.value = true
                    }

                }
            }
        }
    }

    fun amountEntered(amount: String) {
        buttonState.value = amount.isNotEmpty()
        budgetInputStateFlow.value = amount
    }

    fun setBudget() {
        viewModelScope.launch(dispatcherProvider.main) {
            setBudgetUsecase(budgetInputStateFlow.value.toDouble()).collectLatest { usecaseResult ->
                when (usecaseResult) {
                    SetBudgetUsecaseResult.Failure -> {
                        Timber.d("failure")
                    }

                    is SetBudgetUsecaseResult.Success -> {
                        Timber.d("success")
                    }

                }
            }
        }
    }
}

sealed class BudgetUIState {
    data class ExistingBudget(val budget: Budget) : BudgetUIState()
    object Loading : BudgetUIState()
}