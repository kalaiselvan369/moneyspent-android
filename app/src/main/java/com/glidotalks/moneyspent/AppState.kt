package com.glidotalks.moneyspent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.glidotalks.moneyspent.home.HomeScreenBottomNavItem

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
) = remember(navController) {
    AppState(navController)
}

@Stable
class AppState(
    val navHostController: NavHostController
) {

    private val homeScreenBottomNavRoutes = HomeScreenBottomNavItem.routes()

    val shouldShowBottomNavigationBar: Boolean
        @Composable get() = navHostController
            .currentBackStackEntryAsState().value?.destination?.route in homeScreenBottomNavRoutes

    private val currentRoute: String?
        get() = navHostController.currentDestination?.route

    fun upPress() {
        navHostController.navigateUp()
    }

    fun navigateToBottomBarRoute(route: String) {
        if (route != currentRoute) {
            navHostController.navigate(route) {
                launchSingleTop = true
                restoreState = true
                // Pop up backstack to the first destination and save state. This makes going back
                // to the start destination when pressing back in any other bottom tab.
                popUpTo(findStartDestination(navHostController.graph).id) {
                    saveState = true
                }
            }
        }
    }

    fun navigateToOnboardingScreen(from: NavBackStackEntry, route: String = ONBOARDING_ROUTE) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navHostController.navigate(route)
        }
    }

    fun navigateToHome(from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navHostController.navigate(HOME_ROUTE) {
                popUpTo(navHostController.graph.findStartDestination().id) {
                    inclusive = true
                }
            }
        }
    }

    /**
     * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
     *
     * This is used to de-duplicate navigation events.
     */
    private fun NavBackStackEntry.lifecycleIsResumed() =
        this.getLifecycle().currentState == Lifecycle.State.RESUMED

    private val NavGraph.startDestination: NavDestination?
        get() = findNode(startDestinationId)

    /**
     * Copied from similar function in NavigationUI.kt
     *
     * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:navigation/navigation-ui/src/main/java/androidx/navigation/ui/NavigationUI.kt
     */
    private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
        return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
    }
}