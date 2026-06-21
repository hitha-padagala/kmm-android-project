package com.hitha.android.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hitha.android.ui.AppDrawerContent
import com.hitha.android.ui.LoginScreen
import com.hitha.android.ui.SplashScreen
import com.hitha.android.ui.UploadScreen
import com.hitha.android.ui.UserDetailsScreen
import com.hitha.android.ui.UsersScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavGraph(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val openDrawer: () -> Unit = remember { { scope.launch { drawerState.open() } } }

    val closeAndNavigate: (String) -> Unit = remember {
        { route ->
            scope.launch {
                drawerState.close()
                navController.navigate(route) {
                    launchSingleTop = true
                }
            }
        }
    }

    val onLogout: () -> Unit = remember {
        {
            scope.launch {
                drawerState.close()
                navController.navigate(Routes.Login.route) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen || navController.currentDestination?.route in listOf(
            Routes.UserList.route,
            Routes.Upload.route,
            Routes.UserDetails.route
        ),
        drawerContent = {
            AppDrawerContent(
                onNavigateUsers = { closeAndNavigate(Routes.UserList.route) },
                onNavigateUpload = { closeAndNavigate(Routes.Upload.route) },
                onLogout = onLogout
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.Splash.route
        ) {
            composable(Routes.Splash.route) {
                SplashScreen(
                    onSplashFinished = {
                        navController.navigate(Routes.Login.route) {
                            popUpTo(Routes.Splash.route) { inclusive = true }
                        }
                    }
                )
            }

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
                    },
                    onUploadClick = {
                        navController.navigate(Routes.Upload.route)
                    },
                    onOpenDrawer = openDrawer
                )
            }

            composable(Routes.Upload.route) {
                UploadScreen(
                    onBackClick = { navController.popBackStack() },
                    onOpenDrawer = openDrawer
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
                    onBackClick = { navController.popBackStack() },
                    onOpenDrawer = openDrawer
                )
            }
        }
    }
}
