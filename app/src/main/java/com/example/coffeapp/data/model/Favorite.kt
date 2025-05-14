package com.example.coffeapp.data.model

import com.google.gson.annotations.SerializedName

data class Favorite(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("item_name") val itemName: String,
)
