package com.mk.sepetandroid.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("_id")
    val id : String? = "",
    val name : String? = "",
    val description : String? = "",
    val price : Float? = 0F,
    val image : List<String> = emptyList(),
    val discountPrice : Float? = 0F,
    val discountStatus : Boolean = false,
    val count : Int = 0,
)


/*
"id" : findProduct.id,
            "name" : findProduct.name,
            "description" : findProduct.description,
            "price" : findProduct.price,
            "image" : findProduct.image,
            "discountPrice" : findProduct.discountPrice,
            "discountStatus" : findProduct.discountStatus,
 */