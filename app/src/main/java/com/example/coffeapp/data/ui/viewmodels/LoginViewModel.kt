package com.example.coffeapp.data.ui.viewmodels

import androidx.compose.ui.util.trace
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeapp.data.api.ApiService
import com.example.coffeapp.data.local.datastore.DataStoreManager
import com.example.coffeapp.data.model.User
import com.example.coffeapp.data.model.response.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> get() = _loginState
    val userIdFlow: Flow<Int> = dataStoreManager.userId

    fun loginUser(username: String, password: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            try {
                val response = apiService.login(User(username, password))
                val responseCode = response.code()
                if (response.isSuccessful && response.body() != null) {
                    _loginState.postValue(LoginState.Success(response.body()!!))
                    dataStoreManager.saveLoginStatus(true)
                    saveUserId(response.body()!!.userId)
                } else {
                    if (responseCode == 401){
                        _loginState.postValue(LoginState.Error("Email ya da şifre hatalı"))

                    }
//                    _loginState.postValue(LoginState.Error("Giriş başarısız: ${response.message()}"))
                }
            } catch (e: Exception) {
                _loginState.postValue(LoginState.Error("Bir hata oluştu: ${e.message}"))
            }
        }
    }
    fun saveUserId(userId: Int) {
        viewModelScope.launch {
            dataStoreManager.saveUserId(userId)
        }
    }
}

sealed class LoginState {
    object Loading : LoginState()
    data class Success(val response: LoginResponse) : LoginState()
    data class Error(val message: String) : LoginState()
}