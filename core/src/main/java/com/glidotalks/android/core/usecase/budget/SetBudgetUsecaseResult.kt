package com.glidotalks.android.core.usecase.budget

sealed class SetBudgetUsecaseResult {
    object Success : SetBudgetUsecaseResult()
    object Failure : SetBudgetUsecaseResult()
}