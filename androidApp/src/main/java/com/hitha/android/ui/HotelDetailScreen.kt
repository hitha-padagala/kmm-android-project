package com.hitha.android.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hitha.shared.model.Hotel
import com.hitha.shared.model.Room
import com.hitha.shared.repository.BookingRepository
import com.hitha.shared.repository.HotelRepository
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HotelDetailScreen(
    hotelId: Int,
    onBackClick: () -> Unit
) {
    val hotelRepo: HotelRepository = koinInject()
    val bookingRepo: BookingRepository = koinInject()
    var hotel by remember { mutableStateOf<Hotel?>(null) }
    var loading by remember { mutableStateOf(true) }
    var selectedRoom by remember { mutableStateOf<Room?>(null) }
    var showBookingSheet by remember { mutableStateOf(false) }
    var bookingConfirmed by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(hotelId) {
        hotelRepo.getHotel(hotelId).fold(
            onSuccess = { hotel = it },
            onFailure = {}
        )
        loading = false
    }

    if (showBookingSheet && selectedRoom != null) {
        ModalBottomSheet(
            onDismissRequest = { showBookingSheet = false },
            sheetState = sheetState
        ) {
            BookingSheetContent(
                hotel = hotel!!,
                room = selectedRoom!!,
                onConfirm = { guests, specialRequests ->
                    bookingRepo.addBooking(
                        hotelName = hotel!!.name,
                        roomType = selectedRoom!!.type,
                        checkIn = "Tomorrow",
                        checkOut = "Day after tomorrow",
                        guests = guests,
                        totalPrice = selectedRoom!!.price * 2
                    )
                    bookingConfirmed = true
                    showBookingSheet = false
                },
                onDismiss = { showBookingSheet = false }
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(hotel?.name ?: "Hotel Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        when {
            loading -> CircularProgressIndicator(
                modifier = Modifier.fillMaxSize().padding(padding).wrapContentSize(Alignment.Center)
            )
            hotel == null -> Text("Hotel not found", modifier = Modifier.padding(padding).padding(16.dp))
            else -> {
                val h = hotel!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("${h.rating}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(Modifier.width(16.dp))
                        Text(h.location, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }

                    Spacer(Modifier.height(8.dp))
                    Text(h.description, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Spacer(Modifier.height(16.dp))
                    HorizontalDivider()
                    Spacer(Modifier.height(16.dp))

                    Text("Amenities", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        h.amenities.take(6).forEach { amenity ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.Wifi, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                                Text(amenity, fontSize = 11.sp)
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))
                    HorizontalDivider()
                    Spacer(Modifier.height(16.dp))

                    Text("Rooms", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(Modifier.height(8.dp))

                    h.rooms.forEach { room ->
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp).fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(room.type, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                                    Text(room.description, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.People, null, modifier = Modifier.size(14.dp))
                                        Text(" Up to ${room.capacity} guests", fontSize = 12.sp)
                                    }
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text("₹${room.price}", fontWeight = FontWeight.Bold, fontSize = 17.sp, color = MaterialTheme.colorScheme.primary)
                                    Text("/night", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Spacer(Modifier.height(4.dp))
                                    TextButton(
                                        onClick = { selectedRoom = room; showBookingSheet = true }
                                    ) {
                                        Text("Book Now")
                                    }
                                }
                            }
                        }
                    }

                    if (bookingConfirmed) {
                        Spacer(Modifier.height(16.dp))
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Check, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                                Spacer(Modifier.width(12.dp))
                                Text("Booking confirmed!", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BookingSheetContent(
    hotel: Hotel,
    room: Room,
    onConfirm: (Int, String) -> Unit,
    onDismiss: () -> Unit
) {
    Column(modifier = Modifier.padding(24.dp)) {
        Text("Confirm Booking", fontWeight = FontWeight.Bold, fontSize = 22.sp)
        Spacer(Modifier.height(8.dp))
        Text(hotel.name, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
        Text(room.type, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

        Spacer(Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Check-in", fontSize = 14.sp)
            Text("Tomorrow", fontWeight = FontWeight.Medium, fontSize = 14.sp)
        }
        Spacer(Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Check-out", fontSize = 14.sp)
            Text("Day after tomorrow", fontWeight = FontWeight.Medium, fontSize = 14.sp)
        }
        Spacer(Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Guests", fontSize = 14.sp)
            Text("${room.capacity} max", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Spacer(Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Total", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("₹${room.price * 2} (2 nights)", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
        }

        Spacer(Modifier.height(24.dp))
        Button(onClick = { onConfirm(room.capacity, "") }, modifier = Modifier.fillMaxWidth().height(48.dp)) {
            Text("Confirm Booking", fontSize = 16.sp)
        }
        Spacer(Modifier.height(8.dp))
        TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
            Text("Cancel")
        }
    }
}
