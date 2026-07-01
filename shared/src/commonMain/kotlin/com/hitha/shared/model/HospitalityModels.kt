package com.hitha.shared.model

import kotlinx.serialization.Serializable

@Serializable
data class Hotel(
    val id: Int,
    val name: String,
    val location: String,
    val rating: Double,
    val pricePerNight: Double,
    val description: String,
    val amenities: List<String>,
    val imageUrl: String,
    val rooms: List<Room>
)

@Serializable
data class Room(
    val id: Int,
    val type: String,
    val price: Double,
    val capacity: Int,
    val available: Boolean,
    val description: String
)

@Serializable
data class Booking(
    val id: Int,
    val hotelName: String,
    val roomType: String,
    val checkIn: String,
    val checkOut: String,
    val guests: Int,
    val status: String,
    val totalPrice: Double
)

@Serializable
data class MenuItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrl: String
)

@Serializable
data class BookingRequest(
    val hotelId: Int,
    val roomId: Int,
    val checkIn: String,
    val checkOut: String,
    val guests: Int,
    val specialRequests: String
)
