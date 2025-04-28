package com.example.coffeapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_item")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productName: String,
    val price: Double,
    val quantity: Int,
    val imageRes: String
)

