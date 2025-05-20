package com.example.coffeapp.data.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coffeapp.data.ui.viewmodels.FavoriteViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    userId: Int,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val favoriteList by viewModel.favoriteList.observeAsState(emptyList())

    LaunchedEffect(userId) {
        viewModel.getFavorites(userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favoriler") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        if (favoriteList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.LightGray.copy(alpha = 0.5f),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Henüz favori öğe yok",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = innerPadding,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(favoriteList) { favorite ->
                    FavoriteItemCard(favorite.itemName)
                }
            }
        }
    }
}

@Composable
fun FavoriteItemCard(itemName: String?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Favorite Icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = itemName ?: "Bilinmeyen İçecek",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )

        }
    }
}
