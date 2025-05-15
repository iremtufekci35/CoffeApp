package com.example.coffeapp.data.model.response

data class OrderResponse(
    val success: Boolean,
    val message: String,
    val order_id: Int?
)
