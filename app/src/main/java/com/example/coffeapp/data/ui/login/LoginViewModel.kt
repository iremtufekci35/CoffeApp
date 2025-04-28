package com.example.coffeapp.data.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeapp.data.model.User
import com.example.coffeapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> get() = _loginState

    fun loginUser(username: String, password: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            try {
                val user = userRepository.login(username, password)
                if (user != null) {
                    _loginState.postValue(LoginState.Success(user))
                } else {
                    _loginState.postValue(LoginState.Error("Kullanıcı adı veya şifre hatalı"))
                }
            } catch (e: Exception) {
                _loginState.postValue(LoginState.Error("Bir hata oluştu: ${e.message}"))
            }
        }
    }
}

sealed class LoginState {
    object Loading : LoginState()
    data class Success(val user: User) : LoginState()
    data class Error(val message: String) : LoginState()
}
