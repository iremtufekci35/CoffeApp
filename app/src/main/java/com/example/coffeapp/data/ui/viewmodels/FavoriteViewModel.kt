package com.example.coffeapp.data.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeapp.data.api.ApiService
import com.example.coffeapp.data.model.Favorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _favoriteState = MutableLiveData<FavoriteState>()
    val favoriteState: LiveData<FavoriteState> get() = _favoriteState

    private val _favoriteList = MutableLiveData<List<Favorite>>()
    val favoriteList: LiveData<List<Favorite>> get() = _favoriteList

    fun addItemToFav(id: Int,userId: Int, itemName: String){
        viewModelScope.launch {
            try {
                val response = apiService.addToFavorites(Favorite(id,userId,itemName))
                val responseCode = response.code()
                if (response.isSuccessful && response.body() != null) {
                    _favoriteState.value = FavoriteState.Success
                } else {
                    _favoriteState.postValue(FavoriteState.Error("Giriş başarısız: ${response.message()}"))
                }
            } catch (e: Exception) {
                _favoriteState.postValue(FavoriteState.Error("Bir hata oluştu: ${e.message}"))
            }
        }
    }

    fun getFavorites(userId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.getFavorites(userId)
                if (response.isSuccessful && response.body()?.success == true) {
                    _favoriteList.postValue(response.body()?.favorites ?: emptyList())
                } else {
                    // API başarısızsa logla veya hata ver
                }
            } catch (e: Exception) {
                // Hata yönetimi
            }
        }
    }

    suspend fun isFavorite(userId: Int, itemId: Int): Boolean {
        val response = apiService.isFavorite(userId, itemId)
        return response.isSuccessful && response.body()?.isFavorite == true
    }

    suspend fun deleteItemFavorites(userId: Int, itemId: Int): Boolean {
        return try {
            val response = apiService.deleteFromFavorites(userId, itemId)
            response.isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun clearFavorites(userId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.clearFavorites(userId)
                if (response.isSuccessful) {
                    _favoriteList.postValue(emptyList())
                } else {
                    // API başarısızsa logla veya hata ver
                }
            } catch (e: Exception) {
                // Hata yönetimi
            }
        }
    }
}

sealed class FavoriteState {
    object Loading : FavoriteState()
    object Success : FavoriteState()
    data class Error(val message: String) : FavoriteState()
    data class FavList(val favorites: List<Favorite>) : FavoriteState()
}