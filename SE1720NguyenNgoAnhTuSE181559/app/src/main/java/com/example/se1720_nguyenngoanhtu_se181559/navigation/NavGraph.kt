package com.example.se1720_nguyenngoanhtu_se181559.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.se1720_nguyenngoanhtu_se181559.ui.screens.FavoritesScreen
import com.example.se1720_nguyenngoanhtu_se181559.ui.screens.MealDetailScreen
import com.example.se1720_nguyenngoanhtu_se181559.ui.screens.MealListScreen
import com.example.se1720_nguyenngoanhtu_se181559.viewmodel.MealDetailViewModelFactory
import com.example.se1720_nguyenngoanhtu_se181559.viewmodel.ViewModelFactory

sealed class Screen(val route: String) {
    object MealList : Screen("meal_list")
    object MealDetail : Screen("meal_detail/{mealId}") {
        fun createRoute(mealId: String) = "meal_detail/$mealId"
    }
    object Favorites : Screen("favorites")
}

@Composable
fun MealNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.MealList.route
) {
    val context = LocalContext.current
    val viewModelFactory = ViewModelFactory(context)

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.MealList.route) {
            MealListScreen(
                onMealClick = { mealId ->
                    navController.navigate(Screen.MealDetail.createRoute(mealId))
                },
                onNavigateToFavorites = {
                    navController.navigate(Screen.Favorites.route)
                },
                viewModel = viewModel(factory = viewModelFactory)
            )
        }

        composable(
            route = Screen.MealDetail.route,
            arguments = listOf(
                navArgument("mealId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId") ?: return@composable
            MealDetailScreen(
                mealId = mealId,
                onNavigateBack = { navController.popBackStack() },
                viewModel = viewModel(
                    factory = MealDetailViewModelFactory(context, mealId)
                )
            )
        }

        composable(Screen.Favorites.route) {
            FavoritesScreen(
                onNavigateBack = { navController.popBackStack() },
                onMealClick = { mealId ->
                    navController.navigate(Screen.MealDetail.createRoute(mealId))
                },
                viewModel = viewModel(factory = viewModelFactory)
            )
        }
    }
}
