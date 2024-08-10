package com.mk.sepetandroid.presentation.single_product

import com.mk.sepetandroid.domain.model.response.ProductModel
import com.mk.sepetandroid.domain.model.view.DialogModel

data class SingleProductState(
    val productModel: ProductModel?= null,
    val isLoading : Boolean = false,
    val isError : Boolean = false,
    val dialogModel : DialogModel ?= null,
    val message : String ?= "",
    val isSuccess : Boolean = false,
)