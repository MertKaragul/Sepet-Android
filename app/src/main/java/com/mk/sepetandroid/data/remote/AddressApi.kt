package com.mk.sepetandroid.data.remote

import com.mk.sepetandroid.data.remote.dto.AddressDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AddressApi {
    @GET("address/")
    suspend fun getAddress(@Header("Authorization") accessToken : String) : List<AddressDto>

    @PUT("address/")
    suspend fun updateAddress(@Header("Authorization") accessToken : String, @Body addressDto: AddressDto)

    @POST("address/")
    suspend fun addAddress(@Header("Authorization") accessToken : String, @Body addressDto: AddressDto)

    @DELETE("address/{id}")
    suspend fun deleteAddress(@Header("Authorization") accessToken : String, @Path("id") id : String)
}