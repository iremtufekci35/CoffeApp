package com.example.coffeapp.data.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val icon: ImageVector,
    val route: String
) {
    object Home : BottomNavItem(Icons.Default.Home, "home")
    object Favorites : BottomNavItem(Icons.Default.Favorite, "favorites")
    object Cart : BottomNavItem(Icons.Default.ShoppingCart, "cart")
    object Account : BottomNavItem(Icons.Default.Person, "account")
}
