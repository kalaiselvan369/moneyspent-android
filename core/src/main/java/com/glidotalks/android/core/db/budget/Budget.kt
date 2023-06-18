package com.glidotalks.android.core.db.budget

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Budget(
    @PrimaryKey
    @ColumnInfo(name = "monthYear")
    val monthYear: String,
    @ColumnInfo(name = "amount") val amount: Double
)