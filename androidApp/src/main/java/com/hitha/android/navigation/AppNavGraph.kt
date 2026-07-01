package com.hitha.android.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hitha.android.ui.AppDrawerContent
import com.hitha.android.ui.HotelDetailScreen
import com.hitha.android.ui.HotelListScreen
import com.hitha.android.ui.LoginScreen
import com.hitha.android.ui.MyBookingsScreen
import com.hitha.android.ui.RestaurantScreen
import com.hitha.android.ui.SplashScreen
import com.hitha.android.ui.UploadScreen
import com.hitha.android.ui.UserDetailsScreen
import com.hitha.android.ui.UsersScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavGraph(
    navController: NavHostController,
    isDarkTheme: Boolean = false,
    onToggleTheme: () -> Unit = {}
) {
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

    val postLoginRoutes = listOf(
        Routes.UserList.route, Routes.Upload.route, Routes.UserDetails.route,
        Routes.HotelList.route, Routes.HotelDetail.route,
        Routes.Restaurant.route, Routes.MyBookings.route
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen || navController.currentDestination?.route in postLoginRoutes,
        drawerContent = {
            AppDrawerContent(
                onNavigateUsers = { closeAndNavigate(Routes.UserList.route) },
                onNavigateHotels = { closeAndNavigate(Routes.HotelList.route) },
                onNavigateRestaurant = { closeAndNavigate(Routes.Restaurant.route) },
                onNavigateBookings = { closeAndNavigate(Routes.MyBookings.route) },
                onNavigateUpload = { closeAndNavigate(Routes.Upload.route) },
                onLogout = onLogout,
                isDarkTheme = isDarkTheme,
                onToggleTheme = onToggleTheme
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
                    onUserClick = { userId -> navController.navigate(Routes.UserDetails.createRoute(userId)) },
                    onUploadClick = { navController.navigate(Routes.Upload.route) },
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
                arguments = listOf(navArgument("userId") { type = NavType.IntType })
            ) { backStackEntry ->
                val userId = backStackEntry.arguments?.getInt("userId") ?: return@composable
                UserDetailsScreen(
                    userId = userId,
                    onBackClick = { navController.popBackStack() },
                    onOpenDrawer = openDrawer
                )
            }

            composable(Routes.HotelList.route) {
                HotelListScreen(
                    onHotelClick = { hotelId -> navController.navigate(Routes.HotelDetail.createRoute(hotelId)) },
                    onOpenDrawer = openDrawer
                )
            }

            composable(
                route = Routes.HotelDetail.route,
                arguments = listOf(navArgument("hotelId") { type = NavType.IntType })
            ) { backStackEntry ->
                val hotelId = backStackEntry.arguments?.getInt("hotelId") ?: return@composable
                HotelDetailScreen(
                    hotelId = hotelId,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(Routes.Restaurant.route) {
                RestaurantScreen(onOpenDrawer = openDrawer)
            }

            composable(Routes.MyBookings.route) {
                MyBookingsScreen(onOpenDrawer = openDrawer)
            }
        }
    }
}
