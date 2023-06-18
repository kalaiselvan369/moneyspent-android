package com.glidotalks.moneyspent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.glidotalks.moneyspent.home.HomeScreenBottomNavItem
import com.glidotalks.moneyspent.theme.AndroidTemplateTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            TemplateApp()
        }
    }

    @Composable
    fun TemplateApp() {
        val appState = rememberAppState()
        AndroidTemplateTheme(
            darkTheme = false,
            dynamicColor = false
        ) {
            Scaffold(
                bottomBar = {
                    if (appState.shouldShowBottomNavigationBar) {
                        HomeScreenBottomNavigationBar(
                            items = HomeScreenBottomNavItem.items,
                            navController = appState.navHostController,
                            navigateToRoute = appState::navigateToBottomBarRoute
                        )
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController = appState.navHostController,
                    startDestination = HOME_ROUTE,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    appNavGraph(
                        missingOnboarding = appState::navigateToOnboardingScreen,
                        nextOnboardingRoute = appState::navigateToOnboardingScreen,
                        onboardingCompleted = appState::navigateToHome
                    )
                }
            }
        }
    }

    @Composable
    fun HomeScreenBottomNavigationBar(
        items: List<HomeScreenBottomNavItem>,
        navController: NavController,
        navigateToRoute: (String) -> Unit
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        NavigationBar {
            items.forEach { item ->
                val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                NavigationBarItem(
                    selected = selected,
                    onClick = { navigateToRoute(item.route) },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = item.name)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.onSurface,
                        indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}

