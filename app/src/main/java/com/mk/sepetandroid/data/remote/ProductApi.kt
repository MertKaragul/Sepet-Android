package com.mk.sepetandroid.data.remote

import com.mk.sepetandroid.data.remote.dto.ProductDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {
    @GET("products/")
    suspend fun getProducts(
        @Query("min") min : Int?= null,
        @Query("max") max : Int?= null,
        @Query("category") category : List<String> = emptyList(),
        @Query("id") id : List<String> = emptyList()
    ) : List<ProductDto>
}