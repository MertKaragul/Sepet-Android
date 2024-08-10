package com.mk.sepetandroid.domain.model.response

data class ProductModel(
    val id : String? = "",
    val name : String? = "",
    val description : String? = "",
    val price : Float? = 0F,
    val image : List<String> = emptyList(),
    val discountPrice : Float? = 0F,
    val discountStatus : Boolean = false,
)
