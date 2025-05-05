package com.example.coffeapp.data.local.db

import com.example.coffeapp.data.model.CartItem
import com.example.coffeapp.data.model.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppDatabaseImpl(private val appDatabase: AppDatabase) {

    suspend fun insertCartItem(cartItem: CartItem) {
        appDatabase.cartDao().insertCartItem(cartItem)
    }

    suspend fun getAllCartItems(): List<CartItem> {
        return appDatabase.cartDao().getAllCartItems()
    }

    suspend fun deleteCartItem(cartItem: CartItem) {
        appDatabase.cartDao().deleteCartItem(cartItem)
    }

    suspend fun clearCart() {
        appDatabase.cartDao().clearCart()
    }
    suspend fun getCartItemByProductName(name: String): CartItem? {
        return appDatabase.cartDao().getCartItemByProductName(name)
    }
    suspend fun updateCartItem(cartItem: CartItem) {
        return appDatabase.cartDao().updateCartItem(cartItem)
    }

    fun insertUser(users: Users) {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.userDao().insertUser(users)
        }
    }

}
