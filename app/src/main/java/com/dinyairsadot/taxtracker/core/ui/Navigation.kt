package com.dinyairsadot.taxtracker.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dinyairsadot.taxtracker.feature.category.CategoryListScreen

// Simple sealed class to define app routes
sealed class Screen(val route: String) {
    data object CategoryList : Screen("category_list")
}

@Composable
fun TaxTrackerNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.CategoryList.route,
        modifier = modifier
    ) {
        composable(Screen.CategoryList.route) {
            CategoryListScreen()
        }

    }
}
