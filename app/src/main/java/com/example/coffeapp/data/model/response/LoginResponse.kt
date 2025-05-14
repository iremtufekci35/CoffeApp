package com.example.coffeapp.data.model.response


data class LoginResponse(
    val success: Boolean,
    val message: String? = null,
    val token: String,
    val userId: Int
)