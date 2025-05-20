package com.example.coffeapp.data.model

data class CoffeeItem(
    var id: Int,
    val name: String,
    val price: String,
    val imageRes: String,
    var isFavorite: Boolean = false
)
