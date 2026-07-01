package com.hitha.android.navigation

sealed class Routes(val route: String) {
    data object Splash : Routes("splash")
    data object Login : Routes("login")
    data object UserList : Routes("user_list")
    data object Upload : Routes("upload")
    data object UserDetails : Routes("user_details/{userId}") {
        fun createRoute(userId: Int) = "user_details/$userId"
    }
    data object HotelList : Routes("hotel_list")
    data object HotelDetail : Routes("hotel_detail/{hotelId}") {
        fun createRoute(hotelId: Int) = "hotel_detail/$hotelId"
    }
    data object Restaurant : Routes("restaurant")
    data object MyBookings : Routes("my_bookings")
}
