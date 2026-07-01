package com.hitha.shared.repository

import com.hitha.shared.model.Booking

class BookingRepository {

    private val bookings = mutableListOf<Booking>()
    private var nextId = 1

    fun addBooking(
        hotelName: String,
        roomType: String,
        checkIn: String,
        checkOut: String,
        guests: Int,
        totalPrice: Double
    ): Booking {
        val booking = Booking(
            id = nextId++,
            hotelName = hotelName,
            roomType = roomType,
            checkIn = checkIn,
            checkOut = checkOut,
            guests = guests,
            status = "Confirmed",
            totalPrice = totalPrice
        )
        bookings.add(booking)
        return booking
    }

    fun getBookings(): Result<List<Booking>> = Result.success(bookings.toList())
}
