package com.example.coffeapp.data.local.db.dao

import androidx.room.*
import com.example.coffeapp.data.model.CartItem

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem)

    @Delete
    suspend fun deleteCartItem(cartItem: CartItem) : Int

    @Query("SELECT * FROM cart_item")
    suspend fun getAllCartItems(): List<CartItem>

    @Query("DELETE FROM cart_item")
    suspend fun clearCart() : Int
}



