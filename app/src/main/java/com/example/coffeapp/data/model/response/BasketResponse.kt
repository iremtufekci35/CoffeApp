package com.example.coffeapp.data.model.response

import com.google.gson.annotations.SerializedName

data class BasketResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("item_name") val itemName: String,
    @SerializedName("price") val price: Double,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("image_res") val imageRes: String,
    )
data class BasketResponseWrapper(
    val success: Boolean,
    val items: List<BasketResponse>
)