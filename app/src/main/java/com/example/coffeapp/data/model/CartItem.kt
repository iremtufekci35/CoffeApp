package com.example.coffeapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cart_item")
data class CartItem(
    @PrimaryKey(autoGenerate = true) @SerializedName("id")val id: Int = 0,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("item_name") val itemName: String,
    @SerializedName("price") val price: Double,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("image_res") val imageRes: String
)

