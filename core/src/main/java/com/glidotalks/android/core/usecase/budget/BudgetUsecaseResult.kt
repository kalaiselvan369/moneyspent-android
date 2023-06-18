package com.glidotalks.android.core.usecase.budget

import com.glidotalks.android.core.db.budget.Budget

sealed class BudgetUsecaseResult {

    data class Success(val budget: Budget) : BudgetUsecaseResult()
    object Empty : BudgetUsecaseResult()
    object Failure : BudgetUsecaseResult()
}