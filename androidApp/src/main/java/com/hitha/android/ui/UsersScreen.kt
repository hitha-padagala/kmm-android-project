package com.hitha.android.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hitha.android.FavoriteManager
import com.hitha.shared.model.User
import com.hitha.shared.repository.UsersRepository
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    onUserClick: (Int) -> Unit = {},
    onUploadClick: () -> Unit = {},
    onOpenDrawer: () -> Unit = {}
) {
    val repository: UsersRepository = koinInject()
    val scope = rememberCoroutineScope()
    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var isRefreshing by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var favoriteStates by remember { mutableStateOf(mapOf<Int, Boolean>()) }

    suspend fun loadUsers(refresh: Boolean = false) {
        if (refresh) isRefreshing = true
        repository.getUsers().fold(
            onSuccess = {
                users = it
                loading = false
                isRefreshing = false
            },
            onFailure = {
                error = it.message
                loading = false
                isRefreshing = false
            }
        )
    }

    LaunchedEffect(Unit) {
        loadUsers()
    }

    val filteredUsers = remember(users, searchQuery) {
        if (searchQuery.isBlank()) users
        else users.filter {
            it.name.contains(searchQuery, ignoreCase = true) ||
            it.username.contains(searchQuery, ignoreCase = true) ||
            it.email.contains(searchQuery, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Users") },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Open drawer"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onUploadClick) {
                        Icon(
                            imageVector = Icons.Default.CloudUpload,
                            contentDescription = "Upload File",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Search by name, username or email") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                singleLine = true
            )

            when {
                loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )
                }
                error != null -> {
                    Text(
                        text = "Error: $error",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                else -> {
                    PullToRefreshBox(
                        isRefreshing = isRefreshing,
                        onRefresh = { scope.launch { loadUsers(refresh = true) } },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                                top = 8.dp,
                                bottom = 80.dp
                            )
                        ) {
                            items(filteredUsers, key = { it.id }) { user ->
                                val isFav = favoriteStates[user.id] ?: FavoriteManager.isFavorite(user.id)
                                UserCard(
                                    user = user,
                                    isFavorite = isFav,
                                    onClick = { onUserClick(user.id) },
                                    onToggleFavorite = {
                                        val newState = FavoriteManager.toggle(user.id)
                                        favoriteStates = favoriteStates + (user.id to newState)
                                    }
                                )
                            }

                            if (filteredUsers.isEmpty() && searchQuery.isNotBlank()) {
                                item {
                                    Text(
                                        text = "No users found for \"$searchQuery\"",
                                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserCard(
    user: User,
    isFavorite: Boolean = false,
    onClick: () -> Unit = {},
    onToggleFavorite: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = user.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Star else Icons.Outlined.Star,
                        contentDescription = if (isFavorite) "Remove favorite" else "Add favorite",
                        tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "@${user.username}",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 28.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))
            Divider()
            Spacer(modifier = Modifier.height(10.dp))

            InfoRow(Icons.Default.Email, "Email", user.email)
            Spacer(modifier = Modifier.height(6.dp))
            InfoRow(Icons.Default.Phone, "Phone", user.phone)
            Spacer(modifier = Modifier.height(6.dp))
            InfoRow(Icons.Default.Language, "Website", user.website)

            user.address?.let { address ->
                Spacer(modifier = Modifier.height(10.dp))
                Divider()
                Spacer(modifier = Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Address",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${address.street}, ${address.suite}",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 28.dp)
                )
                Text(
                    text = "${address.city}, ${address.zipcode}",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 28.dp)
                )
            }

            user.company?.let { company ->
                Spacer(modifier = Modifier.height(10.dp))
                Divider()
                Spacer(modifier = Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Business,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = company.name,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = company.catchPhrase,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 28.dp)
                )
            }
        }
    }
}

@Composable
private fun InfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = value,
            fontSize = 14.sp
        )
    }
}
