package com.mk.sepetandroid.data.remote

import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.data.remote.dto.TokenDto
import com.mk.sepetandroid.data.remote.dto.ProfileDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApi {
    @POST("auth/login")
    @Headers("Content-Type: application/json")
    suspend fun login(@Body profileDto: ProfileDto) : TokenDto

    @POST("auth/register")
    @Headers("Content-Type: application/json")
    suspend fun register(@Body profileDto: ProfileDto) : ResponseDto

    @GET("auth/profile")
    @Headers("Content-Type: application/json")
    suspend fun profile(@Header("Authorization") authHeader : String) : ProfileDto

    @PUT("auth/profile")
    @Headers("Content-Type: application/json")
    suspend fun putProfile(@Body profileDto: ProfileDto, @Header("Authorization") authHeader : String) : ResponseDto

    @POST("auth/refresh")
    @Headers("Content-Type: application/json")
    suspend fun refresh(@Header("Authorization") refreshToken : String) : TokenDto
}