package com.hitha.android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppDrawerContent(
    onNavigateUsers: () -> Unit,
    onNavigateHotels: () -> Unit,
    onNavigateRestaurant: () -> Unit,
    onNavigateBookings: () -> Unit,
    onNavigateUpload: () -> Unit,
    onLogout: () -> Unit,
    isDarkTheme: Boolean = false,
    onToggleTheme: () -> Unit = {}
) {
    ModalDrawerSheet(modifier = Modifier.fillMaxWidth(0.7f)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Hospitality App",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "KMM Demo",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        HorizontalDivider()

        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Hotel, null, Modifier.size(24.dp)) },
            label = { Text("Hotels") },
            selected = false,
            onClick = onNavigateHotels,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Fastfood, null, Modifier.size(24.dp)) },
            label = { Text("Restaurant") },
            selected = false,
            onClick = onNavigateRestaurant,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        NavigationDrawerItem(
            icon = { Icon(Icons.Default.CalendarMonth, null, Modifier.size(24.dp)) },
            label = { Text("My Bookings") },
            selected = false,
            onClick = onNavigateBookings,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        NavigationDrawerItem(
            icon = { Icon(Icons.Default.People, null, Modifier.size(24.dp)) },
            label = { Text("Users") },
            selected = false,
            onClick = onNavigateUsers,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        NavigationDrawerItem(
            icon = { Icon(Icons.Default.CloudUpload, null, Modifier.size(24.dp)) },
            label = { Text("Upload") },
            selected = false,
            onClick = onNavigateUpload,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 28.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = if (isDarkTheme) "Dark Mode" else "Light Mode",
                modifier = Modifier.weight(1f)
            )
            Switch(checked = isDarkTheme, onCheckedChange = { onToggleTheme() })
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Logout, null, Modifier.size(24.dp)) },
            label = { Text("Logout") },
            selected = false,
            onClick = onLogout,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}
