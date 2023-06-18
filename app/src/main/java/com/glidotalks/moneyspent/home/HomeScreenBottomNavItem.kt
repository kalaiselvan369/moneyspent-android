package com.glidotalks.moneyspent.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.ui.graphics.vector.ImageVector

data class HomeScreenBottomNavItem(
    @StringRes val name: Int,
    val route: String,
    val icon: ImageVector
) {

    companion object {
        val items by lazy {
            listOf(
                HomeScreenBottomNavItem(
                    name = HomeBottomNavRouter.Home.screenNameResId,
                    route = HomeBottomNavRouter.Home.route,
                    icon = Icons.Filled.Home
                ),
                HomeScreenBottomNavItem(
                    name = HomeBottomNavRouter.Budget.screenNameResId,
                    route = HomeBottomNavRouter.Budget.route,
                    icon = Icons.Filled.Wallet,
                ),
                HomeScreenBottomNavItem(
                    name = HomeBottomNavRouter.Expense.screenNameResId,
                    route = HomeBottomNavRouter.Expense.route,
                    icon = Icons.Filled.Paid,
                ),
                HomeScreenBottomNavItem(
                    name = HomeBottomNavRouter.Profile.screenNameResId,
                    route = HomeBottomNavRouter.Profile.route,
                    icon = Icons.Filled.AccountCircle,
                )
            )
        }

        fun routes(): List<String> = items.map { homeScreenNavItem ->
            homeScreenNavItem.route
        }
    }
}
