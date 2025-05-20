package com.example.coffeapp.data.model

import com.google.gson.annotations.SerializedName

data class OrderResponse(
    val success: Boolean,
    val orders: List<Order>
)

data class Order(
    @SerializedName("order_id") val orderId: Int,
    @SerializedName("user_id")val userId: Int,
    @SerializedName("total_price") val totalPrice: Double,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("order_items") val orderItems: List<OrderItem>
)

data class OrderItem(
    @SerializedName("item_name") val itemName: String,
    val quantity: Int,
    val price: Double
)

data class OrderItemRequest(
    val item_name: String,
    val quantity: Int,
    val price: Double
)

data class CreateOrderRequest(
    val user_id: Int,
    val total_price: Double,
    val order_items: List<OrderItemRequest>
)

data class GetOrdersResponse(
    val success: Boolean,
    val orders: List<Order>
)
