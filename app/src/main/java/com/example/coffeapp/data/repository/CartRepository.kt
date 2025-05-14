package com.example.coffeapp.data.repository

import android.util.Log
import com.example.coffeapp.data.api.ApiService
import com.example.coffeapp.data.local.db.AppDatabaseImpl
import com.example.coffeapp.data.model.CartItem
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

//    suspend fun getAllCartItems(): List<CartItem> = appDatabaseImpl.getAllCartItems()
//    suspend fun deleteCartItem(cartItem: CartItem) = appDatabaseImpl.deleteCartItem(cartItem)
//    suspend fun clearCart() = appDatabaseImpl.clearCart()
}
