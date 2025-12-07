package com.dinyairsadot.taxtracker.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dinyairsadot.taxtracker.feature.category.AddCategoryScreen
import com.dinyairsadot.taxtracker.feature.category.CategoryListRoute
import com.dinyairsadot.taxtracker.feature.category.CategoryListViewModel

// Simple sealed class to define app routes
sealed class Screen(val route: String) {
    data object CategoryList : Screen("category_list")
    data object AddCategory : Screen("add_category")
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
        composable(Screen.CategoryList.route) { backStackEntry ->
            val viewModel: CategoryListViewModel = viewModel(backStackEntry)

            CategoryListRoute(
                onAddCategoryClick = {
                    navController.navigate(Screen.AddCategory.route)
                },
                onCategoryClick = { id ->
                    // TODO: later navigate to category details
                },
                viewModel = viewModel
            )
        }

        composable(Screen.AddCategory.route) { backStackEntry ->
            // Get the SAME CategoryListViewModel tied to the CategoryList route
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Screen.CategoryList.route)
            }
            val viewModel: CategoryListViewModel = viewModel(parentEntry)

            AddCategoryScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onSaveCategory = { name, colorHex, description ->
                    viewModel.addCategory(name, colorHex, description)
                }
            )
        }
    }
}
