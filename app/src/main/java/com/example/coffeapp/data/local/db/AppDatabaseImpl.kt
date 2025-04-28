package com.example.coffeapp.data.local.db

import com.example.coffeapp.data.model.CartItem
import com.example.coffeapp.data.model.Product
import com.example.coffeapp.data.model.User
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

    suspend fun insertProduct(product: Product) {
        appDatabase.productDao().insertProduct(product)
    }

    suspend fun getAllProducts(): List<Product> {
        return appDatabase.productDao().getAllProducts()
    }

    suspend fun deleteProduct(product: Product) {
        appDatabase.productDao().deleteProduct(product)
    }

    fun insertUser(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.userDao().insertUser(user)
        }
    }

}
