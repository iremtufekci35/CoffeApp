package com.example.coffeapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import com.example.coffeapp.data.local.datastore.DataStoreManager
import com.example.coffeapp.data.local.db.AppDatabaseImpl
import com.example.coffeapp.data.ui.theme.CoffeeAppTheme
import com.example.coffeapp.data.ui.MainScreen
import com.example.coffeapp.data.ui.viewmodels.FavoriteViewModel
import com.example.coffeapp.data.ui.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    @Inject
    lateinit var appDatabase: AppDatabaseImpl

    private val homeViewModel: HomeViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        lifecycleScope.launch {
//            // userId değerini DataStore'dan oku
//            dataStoreManager.userId.collect { userId ->
//                // ViewModel'e gönder
//                favoriteViewModel.clearFavorites(userId)
//            }
//        }
        setContent {
            val navController = rememberNavController()
            CoffeeAppTheme {
                MainScreen(navController = navController, dataStoreManager = dataStoreManager, homeViewModel = homeViewModel)

            }
        }
    }
}


