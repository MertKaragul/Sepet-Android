package com.mk.sepetandroid.presentation.cart

import com.mk.sepetandroid.domain.model.response.AddressModel
import com.mk.sepetandroid.domain.model.response.CartModel
import com.mk.sepetandroid.domain.model.response.ProductModel

data class CartState(
    val isSuccess : Boolean = false,
    val isError : Boolean = false,
    val isLoading : Boolean = false,
    val message : String ?= "",
    val cart : List<CartModel> = emptyList(),
    val address : List<AddressModel> = emptyList()
)