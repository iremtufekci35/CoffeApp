package com.example.coffeapp.data.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.coffeapp.data.local.datastore.DataStoreManager
import com.example.coffeapp.data.ui.cart.CartScreen
import com.example.coffeapp.data.ui.components.BottomNavItem
import com.example.coffeapp.data.ui.components.BottomNavigationBar
import com.example.coffeapp.data.ui.screens.HomeScreen
import com.example.coffeapp.data.ui.screens.LoginScreen
import com.example.coffeapp.data.ui.screens.ProfileScreen

@Composable
fun MainScreen(
    navController: NavHostController,
    dataStoreManager: DataStoreManager
) {
    val isLoggedIn by dataStoreManager.isLoggedIn.collectAsState(initial = false)

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate(BottomNavItem.Home.route)
        } else {
            navController.navigate("login") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = if (isLoggedIn) BottomNavItem.Home.route else "login",
            Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginScreen(
                    onLoginSuccess = { user ->
                    }
                )
            }
            composable(BottomNavItem.Home.route) { HomeScreen() }
            composable(BottomNavItem.Favorites.route) { FavoritesScreen() }
            composable(BottomNavItem.Cart.route) { CartScreen() }
            composable(BottomNavItem.Account.route) { ProfileScreen() }
        }
    }
}



