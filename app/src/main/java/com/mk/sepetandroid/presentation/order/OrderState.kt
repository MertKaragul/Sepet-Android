package com.mk.sepetandroid.presentation.order

import com.mk.sepetandroid.domain.model.response.CartModel

data class OrderState(
    val isLoading : Boolean = false,
    val isError : Boolean = false,
    val message : String = "",
    val orders : List<CartModel> = emptyList()
)