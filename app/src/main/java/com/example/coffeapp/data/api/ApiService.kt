package com.example.coffeapp.data.api

import com.example.coffeapp.data.model.User
import com.example.coffeapp.data.model.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
     @POST("register")
     suspend fun register(
          @Body user: User
     ): Response<RegisterResponse>
}