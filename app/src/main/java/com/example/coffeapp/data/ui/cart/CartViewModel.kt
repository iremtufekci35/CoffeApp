package com.example.coffeapp.data.ui.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeapp.data.local.db.AppDatabaseImpl
import com.example.coffeapp.data.model.CartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val appDatabaseImpl: AppDatabaseImpl
) : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartItem>>(emptyList())
    val cartItems: LiveData<List<CartItem>> get() = _cartItems

    fun loadCartItems() {
        viewModelScope.launch {
            try {
                val items = appDatabaseImpl.getAllCartItems()
                Log.d("CartViewModel", "Yüklenen öğeler: $items")
                _cartItems.postValue(items)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun removeItem(cartItem: CartItem) {
        viewModelScope.launch {
            try {
                appDatabaseImpl.deleteCartItem(cartItem)
                val updatedList = _cartItems.value?.filter { it.id != cartItem.id }
                _cartItems.postValue(updatedList ?: emptyList())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            try {
                appDatabaseImpl.clearCart()
                _cartItems.postValue(emptyList())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun insertCardItem(cartItem: CartItem) {
        viewModelScope.launch {
            try {
                appDatabaseImpl.insertCartItem(cartItem)
                _cartItems.postValue(listOf(cartItem))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateQuantity(cartItem: CartItem, newQuantity: Int) {
        if (newQuantity >= 0) {
            val updatedItems = _cartItems.value?.map {
                if (it == cartItem) {
                    it.copy(quantity = newQuantity)
                } else {
                    it
                }
            } ?: return

            _cartItems.value = updatedItems
        }
    }
}