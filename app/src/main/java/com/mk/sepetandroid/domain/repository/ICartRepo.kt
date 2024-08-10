package com.mk.sepetandroid.domain.repository

import com.mk.sepetandroid.data.remote.dto.CartActionDto
import com.mk.sepetandroid.data.remote.dto.CartDto

interface ICartRepo {
    suspend fun getCart(accessToken : String,ordered : Boolean) : List<CartDto>
    suspend fun addToCart(accessToken: String, cartActionDto: CartActionDto)
    suspend fun updateCart(accessToken: String, cartActionDto : CartActionDto)
    suspend fun deleteProductCart(accessToken: String, id : String)
    suspend fun order(accessToken: String)
}