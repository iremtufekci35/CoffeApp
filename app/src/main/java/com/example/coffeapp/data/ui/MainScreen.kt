package com.example.coffeapp.data.ui

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.coffeapp.data.local.datastore.DataStoreManager
import com.example.coffeapp.data.ui.screens.CartScreen
import com.example.coffeapp.data.ui.components.BottomNavItem
import com.example.coffeapp.data.ui.components.BottomNavigationBar
import com.example.coffeapp.data.ui.screens.FavoritesScreen
import com.example.coffeapp.data.ui.screens.HomeScreen
import com.example.coffeapp.data.ui.screens.LoginScreen
import com.example.coffeapp.data.ui.screens.OrderScreen
import com.example.coffeapp.data.ui.screens.ProfileScreen
import com.example.coffeapp.data.ui.screens.RegisterScreen
import com.example.coffeapp.data.ui.viewmodels.HomeViewModel
import com.example.coffeapp.data.ui.viewmodels.LoginState
import com.example.coffeapp.data.ui.viewmodels.LoginViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
    dataStoreManager: DataStoreManager,
    loginViewModel: LoginViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel
) {
    val isLoggedIn by dataStoreManager.isLoggedIn.collectAsState(initial = false)
    var logoutRequested by remember { mutableStateOf(false) }
    val loginState by loginViewModel.loginState.observeAsState()
    val userId by dataStoreManager.userId.collectAsState(initial = 0)

    LaunchedEffect(logoutRequested) {
        if (logoutRequested) {
            dataStoreManager.saveLoginStatus(false)
            navController.navigate("login") {
                popUpTo(0) { inclusive = true }
            }
            logoutRequested = false
        }
    }

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate(BottomNavItem.Home.route)
        } else {
            navController.navigate("login") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    when (val state = loginState) {
        is LoginState.Loading -> {
        }
        is LoginState.Success -> {
            Toast.makeText(LocalContext.current, "Login successful!", Toast.LENGTH_SHORT).show()
        }
        is LoginState.Error -> {
            Toast.makeText(LocalContext.current, state.message, Toast.LENGTH_SHORT).show()
        }
        else -> {
        }
    }

    Scaffold(
        bottomBar = {
            if (isLoggedIn) {
                BottomNavigationBar(navController)
            }
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
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    onNavigateToRegister = { navController.navigate("register") }
                )
            }
            composable(BottomNavItem.Home.route) { HomeScreen(dataStoreManager = dataStoreManager) }
            composable(BottomNavItem.Favorites.route) { FavoritesScreen(userId = userId) }
            composable(BottomNavItem.Cart.route) { CartScreen(userId=userId,navController) }
            composable(BottomNavItem.Account.route) {
                ProfileScreen(
                    navController = navController,
                    onOrdersClick = {
                        navController.navigate("order_screen/0")
                    },
                    onLogoutClick = {
                        logoutRequested = true
                    }
                )
            }
            composable("register") {
                RegisterScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onNavigateToHome = {
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }

            composable(
                "order_screen/{cartId}",
                arguments = listOf(navArgument("cartId") { type = NavType.IntType })
            ) { backStackEntry ->
                OrderScreen(navController,userId)
            }
        }
    }
}


