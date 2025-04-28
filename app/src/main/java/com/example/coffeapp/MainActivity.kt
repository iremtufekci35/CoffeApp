package com.example.coffeapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coffeapp.data.local.datastore.DataStoreManager
import com.example.coffeapp.data.local.db.AppDatabaseImpl
import com.example.coffeapp.data.ui.cart.CartScreen
import com.example.coffeapp.data.ui.home_screen.HomeScreen
import com.example.coffeapp.data.ui.login.LoginScreen
import com.example.coffeapp.data.ui.theme.CoffeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var dataStoreManager: DataStoreManager
    @Inject
    lateinit var appDatabase: AppDatabaseImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoffeAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }

    @Composable
    fun AppNavigation() {
        val navController = rememberNavController()
        val isLoggedIn by dataStoreManager.isLoggedIn.collectAsState(initial = false)

        val startDestination = if (isLoggedIn) "home_screen" else "login"

        NavHost(navController = navController, startDestination = startDestination) {
            composable("login") {
                LoginScreen(onLoginSuccess = { user ->
                    CoroutineScope(Dispatchers.IO).launch {
                        dataStoreManager.saveLoginStatus(true)
                    }
                    navController.navigate("home_screen") {
                        popUpTo("login") { inclusive = true }
                    }
                })
            }

            composable("home_screen") {
                HomeScreen()
            }
            composable("cart_screen") {
                CartScreen()
            }
        }
    }
}

