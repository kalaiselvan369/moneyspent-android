package com.glidotalks.android.core.usecase.budget

import com.glidotalks.android.core.db.budget.BudgetDao
import com.glidotalks.android.core.dispatchers.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class BudgetModule {

    @Provides
    fun provideBudgetUsecase(
        dispatcherProvider: DispatcherProvider,
        budgetDao: BudgetDao
    ) = BudgetUsecase(dispatcherProvider, budgetDao)


    @Provides
    fun provideSetBudgetUsecase(
        dispatcherProvider: DispatcherProvider,
        budgetDao: BudgetDao
    ) = SetBudgetUsecase(dispatcherProvider, budgetDao)
}