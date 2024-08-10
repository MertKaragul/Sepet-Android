package com.mk.sepetandroid.domain.model.response

import com.mk.sepetandroid.data.remote.dto.ProductDto

data class CartModel(
    val id : String,
    val products : List<CartProductsModel> = emptyList(),
    val createdDate : String,
    val total : Double,
    var address : AddressModel?,
)

data class CartProductsModel(
    val product : ProductModel,
    val count : Int
)

data class CartActionModel(
    val productId : String?=null,
    val count : Int?=null,
    val address : String?=null,
)
