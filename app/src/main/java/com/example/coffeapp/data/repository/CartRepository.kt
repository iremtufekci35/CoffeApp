package com.example.coffeapp.data.repository

import android.util.Log
import com.example.coffeapp.data.api.ApiService
import com.example.coffeapp.data.local.db.AppDatabaseImpl
import com.example.coffeapp.data.model.CartItem
import com.example.coffeapp.data.model.CreateOrderRequest
import com.example.coffeapp.data.model.Order
import com.example.coffeapp.data.model.OrderItemRequest
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val appDatabaseImpl: AppDatabaseImpl,
    private val apiService: ApiService
) {

    suspend fun insertCartItem(cartItem: CartItem) {
        val existingItem = appDatabaseImpl.getCartItemByProductName(cartItem.itemName)
        if (existingItem != null) {
            val updatedItem =
                existingItem.copy(quantity = existingItem.quantity + cartItem.quantity)
            appDatabaseImpl.updateCartItem(updatedItem)
        } else {
            appDatabaseImpl.insertCartItem(cartItem)
        }

        try {
            val response = apiService.addToBasket(
                CartItem(
                    cartItem.id,
                    cartItem.userId,
                    cartItem.itemName,
                    cartItem.price,
                    cartItem.quantity,
                    cartItem.imageRes
                )
            )
            if (!response.isSuccessful) {
                Log.e("CartRepository", "API hatası: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("CartRepository", "API'ye gönderim başarısız", e)
        }
    }

    suspend fun fetchCartFromApi(userId: Int): List<CartItem> {
        val response = apiService.getBasket(userId)
        return if (response.isSuccessful) {
            response.body()?.items?.map { item ->
                CartItem(
                    id = item.id,
                    userId = item.userId,
                    itemName = item.itemName,
                    price = item.price,
                    quantity = item.quantity,
                    imageRes = item.imageRes
                )
            } ?: emptyList()
        } else {
            emptyList()
        }
    }
    suspend fun submitOrder(userId: Int, items: List<CartItem>): Boolean {
        return try {
            val totalPrice = items.sumOf { it.price * it.quantity }

            val orderRequest = CreateOrderRequest(
                user_id = userId,
                total_price = totalPrice,
                order_items = items.map {
                    OrderItemRequest(
                        item_name = it.itemName,
                        quantity = it.quantity,
                        price = it.price
                    )
                }
            )

            val response = apiService.createOrder(orderRequest)
            if (response.isSuccessful && response.body()?.success == true) {
                true
            } else {
                Log.e("CartRepository", "Order API failed: ${response.code()} - ${response.body()?.success}")
                false
            }
        } catch (e: Exception) {
            Log.e("CartRepository", "Order creation failed", e)
            false
        }
    }
    suspend fun getOrder(userId: Int): List<Order> {
        return try {
            val response = apiService.getOrder(userId)
            if (response.isSuccessful && response.body()?.success == true) {
                response.body()?.orders ?: emptyList()
            } else {
                Log.e("CartRepository", "Get Order API failed: ${response.code()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("CartRepository", "Get Order API exception", e)
            emptyList()
        }
    }
    suspend fun clearCart(userId: Int) {
        try {
            val response = apiService.clearCart(userId)
            if (!response.isSuccessful) {
                Log.e("CartRepository", "Clear Cart API failed: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("CartRepository", "Clear Cart API exception", e)
        }
    }
}

//    suspend fun getAllCartItems(): List<CartItem> = appDatabaseImpl.getAllCartItems()
//    suspend fun deleteCartItem(cartItem: CartItem) = appDatabaseImpl.deleteCartItem(cartItem)
//    suspend fun clearCart() = appDatabaseImpl.clearCart()

