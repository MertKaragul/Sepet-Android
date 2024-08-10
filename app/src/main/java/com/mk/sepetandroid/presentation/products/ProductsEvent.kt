package com.mk.sepetandroid.presentation.products

sealed class ProductsEvent {
    data class GetProducts(
        val min : Int,
        val max : Int,
        val category : String?= null
    ) : ProductsEvent()
}