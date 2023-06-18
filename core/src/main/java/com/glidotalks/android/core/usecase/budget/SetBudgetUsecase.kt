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

class SetBudgetUsecase(
    dispatcherProvider: DispatcherProvider,
    private val budgetDao: BudgetDao
) : FlowUsecase<Double, SetBudgetUsecaseResult>(dispatcherProvider.io) {

    override suspend fun execute(param: Double): Flow<SetBudgetUsecaseResult> {
        return flow {
            val currentMonthYear = DateTimeConverters.getMonthYear(System.currentTimeMillis())
            val budget = Budget(
                monthYear = currentMonthYear,
                amount = param
            )
            Timber.d("to save %s", budget)
            if (toUpdate(currentMonthYear)) {
                budgetDao.update(budget)
            } else {
                budgetDao.insert(budget)
            }
            emit(SetBudgetUsecaseResult.Success)
        }
    }

    override suspend fun exception(flowCollector: FlowCollector<SetBudgetUsecaseResult>) {
        flowCollector.emit(SetBudgetUsecaseResult.Failure)
    }

    private fun toUpdate(currentMonthYear: String): Boolean {
        val budgets = budgetDao.getAll()
        for (budget in budgets) {
            if (budget.monthYear == currentMonthYear) {
                return true
            }
        }
        return false
    }
}