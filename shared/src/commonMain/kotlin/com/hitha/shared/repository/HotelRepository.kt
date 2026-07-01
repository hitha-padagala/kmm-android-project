package com.hitha.shared.repository

import com.hitha.shared.model.Hotel
import com.hitha.shared.model.Room

class HotelRepository {

    private val hotels = listOf(
        Hotel(
            id = 1,
            name = "Grand Palace Hotel",
            location = "New Delhi, India",
            rating = 4.8,
            pricePerNight = 250.0,
            description = "Luxury 5-star hotel in the heart of the capital, featuring world-class dining, spa, and panoramic city views.",
            amenities = listOf("WiFi", "Pool", "Spa", "Gym", "Restaurant", "Bar", "Room Service", "Parking"),
            imageUrl = "https://images.unsplash.com/photo-1566073771259-6a8506099945?w=600",
            rooms = listOf(
                Room(1, "Deluxe Room", 250.0, 2, true, "King bed, city view, 400 sq ft"),
                Room(2, "Premium Suite", 450.0, 3, true, "King bed, living area, 700 sq ft"),
                Room(3, "Presidential Suite", 1200.0, 4, true, "2 bedrooms, dining room, 1500 sq ft")
            )
        ),
        Hotel(
            id = 2,
            name = "Ocean View Resort & Spa",
            location = "Goa, India",
            rating = 4.6,
            pricePerNight = 320.0,
            description = "Beachfront resort with private beach access, infinity pool, and award-winning Ayurvedic spa treatments.",
            amenities = listOf("WiFi", "Beach Access", "Pool", "Spa", "Restaurant", "Bar", "Yoga Deck", "Water Sports"),
            imageUrl = "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?w=600",
            rooms = listOf(
                Room(4, "Garden View", 220.0, 2, true, "Queen bed, garden balcony, 350 sq ft"),
                Room(5, "Sea View Suite", 420.0, 2, true, "King bed, ocean balcony, 550 sq ft"),
                Room(6, "Beach Villa", 800.0, 4, true, "Private pool, direct beach access")
            )
        ),
        Hotel(
            id = 3,
            name = "Heritage Haveli",
            location = "Jaipur, India",
            rating = 4.7,
            pricePerNight = 180.0,
            description = "Restored 18th-century haveli offering an authentic Rajasthani experience with traditional architecture and cuisine.",
            amenities = listOf("WiFi", "Courtyard Pool", "Heritage Tours", "Restaurant", "Cultural Shows", "Rooftop Dining"),
            imageUrl = "https://images.unsplash.com/photo-1582719508461-905c673771fd?w=600",
            rooms = listOf(
                Room(7, "Heritage Room", 180.0, 2, true, "Traditional decor, courtyard view, 300 sq ft"),
                Room(8, "Maharaja Suite", 380.0, 3, true, "Regal furnishings, jharokha balcony, 600 sq ft")
            )
        ),
        Hotel(
            id = 4,
            name = "Mountain Peak Retreat",
            location = "Manali, Himachal Pradesh",
            rating = 4.5,
            pricePerNight = 150.0,
            description = "Cozy mountain resort surrounded by pine forests with breathtaking Himalayan views and adventure activities.",
            amenities = listOf("WiFi", "Bonfire", "Trekking", "Restaurant", "Indoor Games", "Heating", "Mountain View"),
            imageUrl = "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?w=600",
            rooms = listOf(
                Room(9, "Standard Room", 150.0, 2, true, "Double bed, mountain view, 280 sq ft"),
                Room(10, "Family Cottage", 290.0, 4, true, "2 bedrooms, living room, fireplace"),
                Room(11, "Luxury Chalet", 550.0, 2, true, "Private balcony, jacuzzi, 500 sq ft")
            )
        ),
        Hotel(
            id = 5,
            name = "Backwater Serenity",
            location = "Alleppey, Kerala",
            rating = 4.9,
            pricePerNight = 200.0,
            description = "Traditional houseboat stays and waterfront cottages on the serene backwaters of Kerala with authentic Ayurvedic wellness.",
            amenities = listOf("WiFi", "Houseboat", "Ayurveda Spa", "Restaurant", "Fishing", "Canoeing", "Yoga"),
            imageUrl = "https://images.unsplash.com/photo-1590073242678-70ee3fc28e8e?w=600",
            rooms = listOf(
                Room(12, "Waterfront Cottage", 200.0, 2, true, "Lake-facing, private sit-out, 350 sq ft"),
                Room(13, "Premium Houseboat", 350.0, 2, true, "AC bedroom, upper deck, crew included"),
                Room(14, "Luxury Houseboat", 500.0, 4, true, "2 bedrooms, living room, full crew")
            )
        )
    )

    fun getHotels(): Result<List<Hotel>> = Result.success(hotels)

    fun getHotel(id: Int): Result<Hotel> {
        return hotels.find { it.id == id }
            ?.let { Result.success(it) }
            ?: Result.failure(NoSuchElementException("Hotel not found"))
    }
}
