package com.mk.sepetandroid.data.remote

import com.mk.sepetandroid.data.remote.dto.CartActionDto
import com.mk.sepetandroid.data.remote.dto.CartDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CartApi {
    @POST("cart/")
    suspend fun addToCart(@Header("Authorization") accessToken : String, @Body cartActionDto: CartActionDto)

    @GET("cart/{ordered}")
    suspend fun getCart(@Header("Authorization") accessToken : String, @Path("ordered") ordered : Boolean) : List<CartDto>

    @POST("cart/order")
    suspend fun order(@Header("Authorization") accessToken : String)

    @PUT("cart/")
    suspend fun updateCart(@Header("Authorization") accessToken : String, @Body cartActionDto: CartActionDto)

    @DELETE("cart/{id}")
    suspend fun deleteCart(@Header("Authorization") accessToken : String, @Path("id")  id : String)
}