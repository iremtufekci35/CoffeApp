package com.example.coffeapp.data.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeapp.data.api.ApiService
import com.example.coffeapp.data.model.User
import com.example.coffeapp.data.model.Users
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _registerState = MutableLiveData<RegisterState>()
    val registerState: LiveData<RegisterState> = _registerState

    fun registerUser(user: User) {
        println("KAYIT BASLADI")
        viewModelScope.launch {
            try {
                _registerState.value = RegisterState.Loading
                val response = withContext(Dispatchers.IO) {
                    apiService.register(user)
                }
                if (response.isSuccessful) {
                    println("KAYIT BASARILI")
                    _registerState.value = RegisterState.Success
                } else {
                    println("KAYIT BASARISIZ: ${response.message()}")
                    _registerState.value = RegisterState.Error("Kayıt başarısız: ${response.message()}")
                }
            } catch (e: Exception) {
                println("Error during registration: ${e.localizedMessage}")
                _registerState.value = RegisterState.Error("Hata: ${e.localizedMessage}")
            }
        }
    }
}

sealed class RegisterState {
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}
