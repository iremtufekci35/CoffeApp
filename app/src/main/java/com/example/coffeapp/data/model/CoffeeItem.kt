package com.example.coffeapp.data.model

data class CoffeeItem(
    val name: String,
    val price: String,
    val imageRes: String,
    var isFavorite: Boolean = false
)
