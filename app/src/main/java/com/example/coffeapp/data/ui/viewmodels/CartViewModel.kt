package com.example.coffeapp.data.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeapp.data.model.CartItem
import com.example.coffeapp.data.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartItem>>(emptyList())
    val cartItems: LiveData<List<CartItem>> get() = _cartItems

    fun loadCartItems(userId: Int) {
        viewModelScope.launch {
            val items = cartRepository.fetchCartFromApi(userId)
            _cartItems.postValue(items)
        }
    }
    fun fetchCartItems(userId: Int) {
        viewModelScope.launch {
            val items = cartRepository.fetchCartFromApi(userId)
            _cartItems.postValue(items)
        }
    }
    fun insertCartItem(cartItem: CartItem,userId: Int) {
        viewModelScope.launch {
            cartRepository.insertCartItem(cartItem)
            fetchCartItems(userId)
        }
    }
//
//    fun removeItem(cartItem: CartItem) {
//        viewModelScope.launch {
//            cartRepository.deleteCartItem(cartItem)
//            loadCartItems()
//        }
//    }
//
//    fun clearCart() {
//        viewModelScope.launch {
//            cartRepository.clearCart()
//            _cartItems.postValue(emptyList())
//        }
//    }

    fun updateQuantity(cartItem: CartItem, newQuantity: Int) {
        if (newQuantity >= 0) {
            val updatedItems = _cartItems.value?.map {
                if (it == cartItem) it.copy(quantity = newQuantity) else it
            } ?: return
            _cartItems.value = updatedItems
        }
    }
}
