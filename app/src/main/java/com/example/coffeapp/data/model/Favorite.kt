package com.example.coffeapp.data.model

import com.google.gson.annotations.SerializedName

data class Favorite(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("item_name") val itemName: String,
)
