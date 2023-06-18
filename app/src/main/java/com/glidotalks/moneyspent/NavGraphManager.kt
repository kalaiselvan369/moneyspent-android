package com.glidotalks.moneyspent

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.glidotalks.moneyspent.budget.BudgetScreen
import com.glidotalks.moneyspent.budget.BudgetViewModel
import com.glidotalks.moneyspent.expense.AddExpenseViewModel
import com.glidotalks.moneyspent.expense.ExpenseScreen
import com.glidotalks.moneyspent.home.HomeBottomNavRouter
import com.glidotalks.moneyspent.home.HomeScreen
import com.glidotalks.moneyspent.onboarding.ChooseCurrencyViewModel
import com.glidotalks.moneyspent.onboarding.SetCurrencyScreen
import com.glidotalks.moneyspent.onboarding.WelcomeScreen
import com.glidotalks.moneyspent.onboarding.WelcomeViewModel

const val HOME_ROUTE = "root"
const val ONBOARDING_ROUTE = "onboarding"

fun NavGraphBuilder.appNavGraph(
    missingOnboarding: (NavBackStackEntry) -> Unit,
    nextOnboardingRoute: (NavBackStackEntry, String) -> Unit,
    onboardingCompleted: (NavBackStackEntry) -> Unit
) {
    homeNavGraph(missingOnboarding)
    onboardingGraph(nextOnboardingRoute, onboardingCompleted)
}

fun NavGraphBuilder.homeNavGraph(missingOnboarding: (NavBackStackEntry) -> Unit) {
    navigation(startDestination = HomeBottomNavRouter.Home.route, route = HOME_ROUTE) {
        composable(HomeBottomNavRouter.Home.route) {
            HomeScreen(missingOnboardingInfo = {
                missingOnboarding(it)
            })
        }

        composable(HomeBottomNavRouter.Expense.route) {
            val viewModel = hiltViewModel<AddExpenseViewModel>()
            ExpenseScreen(viewModel)
        }

        composable(HomeBottomNavRouter.Budget.route) {
            val viewModel = hiltViewModel<BudgetViewModel>()
            BudgetScreen(viewModel)
        }
        composable(HomeBottomNavRouter.Profile.route) {

        }

    }
}

fun NavGraphBuilder.onboardingGraph(
    nextRoute: (NavBackStackEntry, String) -> Unit,
    onboardingCompleted: (NavBackStackEntry) -> Unit
) {
    navigation(startDestination = "welcome", route = ONBOARDING_ROUTE) {
        composable("welcome") {
            val viewModel = hiltViewModel<WelcomeViewModel>()
            WelcomeScreen(viewModel) {
                nextRoute(it, "currency")
            }
        }

        composable("currency") {
            val viewModel = hiltViewModel<ChooseCurrencyViewModel>()
            SetCurrencyScreen(viewModel) {
                onboardingCompleted(it)
            }
        }
    }
}

