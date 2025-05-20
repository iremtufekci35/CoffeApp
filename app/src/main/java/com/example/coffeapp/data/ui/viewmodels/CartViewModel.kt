package com.example.coffeapp.data.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeapp.data.model.CartItem
import com.example.coffeapp.data.model.Order
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

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> = _orders

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

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

    fun insertCartItem(cartItem: CartItem, userId: Int) {
        viewModelScope.launch {
            cartRepository.insertCartItem(cartItem)
            fetchCartItems(userId)
        }
    }

    fun clearCart(userId: Int) {
        viewModelScope.launch {
            cartRepository.clearCart(userId)
            _cartItems.postValue(emptyList())
        }
    }


    fun updateQuantity(cartItem: CartItem, newQuantity: Int) {
        if (newQuantity >= 0) {
            val updatedItems = _cartItems.value?.map {
                if (it == cartItem) it.copy(quantity = newQuantity) else it
            } ?: return
            _cartItems.value = updatedItems
        }
    }

    fun createOrder(
        userId: Int,
        items: List<CartItem>,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            val success = cartRepository.submitOrder(userId, items)
            if (success) {
                onSuccess()
            } else {
                onError("Sipariş oluşturulamadı.")
            }
        }
    }

    fun fetchOrders(userId: Int) {
        viewModelScope.launch {
            val orders = cartRepository.getOrder(userId)
            println("orders:$orders")
            if (orders.isNotEmpty()) {
                _orders.postValue(orders)
            } else {
                _error.postValue("Siparişler bulunamadı veya hata oluştu.")
            }
        }
    }
}
