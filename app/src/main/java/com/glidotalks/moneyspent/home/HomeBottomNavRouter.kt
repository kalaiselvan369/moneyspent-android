package com.glidotalks.moneyspent.home

import androidx.annotation.StringRes
import com.glidotalks.moneyspent.R

const val HOME = "home"
const val BUDGET = "budget"
const val EXPENSE = "expense"
const val PROFILE = "profile"

sealed class HomeBottomNavRouter(
    val route: String,
    @StringRes val screenNameResId: Int
) {
    object Home : HomeBottomNavRouter(HOME, R.string.home)
    object Budget : HomeBottomNavRouter(BUDGET, R.string.budget)
    object Expense : HomeBottomNavRouter(EXPENSE, R.string.expense)
    object Profile : HomeBottomNavRouter(PROFILE, R.string.profile)
}