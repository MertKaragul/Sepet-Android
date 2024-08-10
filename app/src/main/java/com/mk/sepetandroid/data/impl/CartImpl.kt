package com.mk.sepetandroid.data.impl

import com.mk.sepetandroid.data.remote.CartApi
import com.mk.sepetandroid.data.remote.dto.CartActionDto
import com.mk.sepetandroid.data.remote.dto.CartDto
import com.mk.sepetandroid.domain.repository.ICartRepo
import retrofit2.http.Body
import javax.inject.Inject

class CartImpl @Inject constructor(
    private val cartApi: CartApi
) : ICartRepo {
    override suspend fun getCart(accessToken: String,ordered : Boolean) : List<CartDto> {
        return cartApi.getCart(accessToken, ordered)
    }

    override suspend fun addToCart(accessToken: String, cartActionDto: CartActionDto) {
        cartApi.addToCart(accessToken,cartActionDto)
    }

    override suspend fun updateCart(accessToken: String, cartActionDto: CartActionDto) {
        cartApi.updateCart(accessToken,cartActionDto)
    }

    override suspend fun deleteProductCart(accessToken: String, id : String) {
        cartApi.deleteCart(accessToken, id)
    }

    override suspend fun order(accessToken: String) {
        cartApi.order(accessToken)
    }

}