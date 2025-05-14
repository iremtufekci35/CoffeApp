package com.example.coffeapp.data.model.response

import com.example.coffeapp.data.model.Favorite

data class FavoriteResponse(
    val success: Boolean,
    val message: String? = null,
    val favorites: List<Favorite>
)