package com.mk.sepetandroid.presentation.products

import com.mk.sepetandroid.domain.model.response.CategoryModel
import com.mk.sepetandroid.domain.model.response.ProductModel

data class ProductsState(
    val products : List<ProductModel> = emptyList(),
    val categories : List<CategoryModel> = emptyList(),
    val isError : Boolean = false,
    val message : String? = "",
    val isLoading : Boolean = false,
    val min : Int = 0,
    val max : Int = 15,
    val category : String ?= null,
    val endSearch : Boolean = false
)