package com.glidotalks.android.core.db

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideBudgetDao(appDatabase: AppDatabase) = appDatabase.budgetDao()
}