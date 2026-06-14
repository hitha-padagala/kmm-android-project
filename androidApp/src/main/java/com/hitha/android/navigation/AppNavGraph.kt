package com.hitha.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hitha.android.ui.LoginScreen
import com.hitha.android.ui.UserDetailsScreen
import com.hitha.android.ui.UsersScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.Login.route
    ) {
        composable(Routes.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.UserList.route) {
                        popUpTo(Routes.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.UserList.route) {
            UsersScreen(
                onUserClick = { userId ->
                    navController.navigate(Routes.UserDetails.createRoute(userId))
                }
            )
        }

        composable(
            route = Routes.UserDetails.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: return@composable
            UserDetailsScreen(
                userId = userId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
