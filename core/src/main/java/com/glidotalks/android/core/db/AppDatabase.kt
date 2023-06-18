package com.glidotalks.android.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.glidotalks.android.core.db.budget.Budget
import com.glidotalks.android.core.db.budget.BudgetDao

@Database(entities = [Budget::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun budgetDao(): BudgetDao
}