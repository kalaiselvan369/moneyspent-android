package com.glidotalks.android.core.usecase.budget

import com.glidotalks.android.core.db.budget.Budget
import com.glidotalks.android.core.db.budget.BudgetDao
import com.glidotalks.android.core.dispatchers.DispatcherProvider
import com.glidotalks.android.core.usecase.FlowUsecase
import com.glidotalks.android.core.util.DateTimeConverters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class BudgetUsecase(
    dispatcherProvider: DispatcherProvider,
    private val budgetDao: BudgetDao
) : FlowUsecase<Unit, BudgetUsecaseResult>(dispatcherProvider.default) {
    override suspend fun execute(param: Unit): Flow<BudgetUsecaseResult> {
        return flow {
            val dbResult = budgetDao.getAll()
            val usecaseResult = getUsecaseResult(dbResult)
            emit(usecaseResult)
        }
    }

    override suspend fun exception(flowCollector: FlowCollector<BudgetUsecaseResult>) {
        flowCollector.emit(BudgetUsecaseResult.Failure)
    }

    private fun getUsecaseResult(budget: List<Budget>?): BudgetUsecaseResult {
        return when {
            budget == null -> BudgetUsecaseResult.Failure
            budget.isEmpty() -> BudgetUsecaseResult.Empty
            else -> processData(budget)
        }
    }

    private fun processData(budgets: List<Budget>): BudgetUsecaseResult {
        val currentMonthYear = DateTimeConverters.getMonthYear(System.currentTimeMillis())
        for (budget in budgets) {
            Timber.d("loop budget %s", budget)
            if (budget.monthYear == currentMonthYear) {
                return BudgetUsecaseResult.Success(budget)
            }
        }
        return BudgetUsecaseResult.Empty
    }
}