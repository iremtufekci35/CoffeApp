package com.example.coffeapp.data.api

import com.example.coffeapp.data.model.CartItem
import com.example.coffeapp.data.model.CreateOrderRequest
import com.example.coffeapp.data.model.Favorite
import com.example.coffeapp.data.model.GetOrdersResponse
import com.example.coffeapp.data.model.OrderResponse
import com.example.coffeapp.data.model.User
import com.example.coffeapp.data.model.response.BasketResponse
import com.example.coffeapp.data.model.response.BasketResponseWrapper
import com.example.coffeapp.data.model.response.FavoriteResponse
import com.example.coffeapp.data.model.response.IsFavoriteResponse
import com.example.coffeapp.data.model.response.LoginResponse
import com.example.coffeapp.data.model.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
     @POST("register")
     suspend fun register(@Body user: User): Response<RegisterResponse>

     @POST("login")
     suspend fun login(@Body user: User): Response<LoginResponse>

     @POST("favorites")
     suspend fun addToFavorites(@Body favorite: Favorite): Response<FavoriteResponse>

     @POST("basket/add")
     suspend fun addToBasket(@Body cartItem: CartItem): Response<BasketResponse>

     @POST("order")
     suspend fun createOrder(@Body orderRequest: CreateOrderRequest): Response<OrderResponse>

     @GET("order/{userId}")
     suspend fun getOrder(@Path("userId") userId: Int): Response<GetOrdersResponse>

     @GET("favorites/{userId}")
     suspend fun getFavorites(@Path("userId") userId: Int): Response<FavoriteResponse>

     @GET("favorites/{userId}/{itemId}")
     suspend fun isFavorite(@Path("userId") userId: Int, @Path("itemId") itemId: Int): Response<IsFavoriteResponse>

     @GET("basket/{userId}")
     suspend fun getBasket(@Path("userId") userId: Int): Response<BasketResponseWrapper>

     @DELETE("basket/item/{itemId}")
     suspend fun deleteCartItem(@Path("itemId") itemId: Int): Response<Unit>

     @DELETE("basket/clear/{userId}")
     suspend fun clearCart(@Path("userId") userId: Int): Response<Unit>

     @DELETE("favorites/{userId}/{itemId}")
     suspend fun deleteFromFavorites(@Path("userId") userId: Int, @Path("itemId") itemId: Int): Response<IsFavoriteResponse>

     @DELETE("favorites/clear/{userId}")
     suspend fun clearFavorites(@Path("userId") userId: Int): Response<Unit>
}