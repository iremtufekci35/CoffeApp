package com.example.coffeapp.data.model

data class OrderResponse(
    val success: Boolean,
    val orders: List<Order>
)

data class Order(
    val id: Int,
    val user_id: Int,
    val total_price: Double,
    val created_at: String,
    val order_items: List<OrderItem>
)

data class OrderItem(
    val item_name: String,
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
