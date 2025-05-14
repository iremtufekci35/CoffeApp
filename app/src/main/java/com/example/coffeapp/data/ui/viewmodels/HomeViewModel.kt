package com.example.coffeapp.data.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeapp.data.local.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val isLoggedIn = dataStoreManager.isLoggedIn
    val userId = dataStoreManager.userId

    fun checkUserStatus() {
        viewModelScope.launch {
            isLoggedIn.collect { isLoggedIn ->
            }
        }
    }
}
