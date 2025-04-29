package com.example.coffeapp.data.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: String
) {
    object Home : BottomNavItem("Ana Sayfa", Icons.Default.Home, "home")
    object Favorites : BottomNavItem("Favoriler", Icons.Default.Favorite, "favorites")
    object Cart : BottomNavItem("Sepetim", Icons.Default.ShoppingCart, "cart")
    object Account : BottomNavItem("Hesabım", Icons.Default.Person, "account")
}
