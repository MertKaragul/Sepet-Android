package com.mk.sepetandroid.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CartDto(
    @SerializedName("_id")
    val id : String,
    val products : List<CartProductsDto> = emptyList(),
    val createdDate : String,
    val total : Double,
    val address : AddressDto?,
)

data class CartProductsDto(
    val product : ProductDto,
    val count : Int
)


data class CartActionDto(
    val productId : String?=null,
    val count : Int?=null,
    val address : String?=null
)