package com.glidotalks.android.core.db.budget

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BudgetDao {

    @Query("SELECT * FROM budget")
    fun getAll(): List<Budget>

    @Insert
    fun insert(budget: Budget)

    @Update
    fun update(budget: Budget)

    @Delete
    fun delete(budget: Budget)
}