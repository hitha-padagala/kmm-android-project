package com.hitha.android.navigation

sealed class Routes(val route: String) {
    data object UserList : Routes("user_list")
    data object UserDetails : Routes("user_details/{userId}") {
        fun createRoute(userId: Int) = "user_details/$userId"
    }
}
