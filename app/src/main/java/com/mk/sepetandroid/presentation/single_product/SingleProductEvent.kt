package com.mk.sepetandroid.presentation.single_product

import com.mk.sepetandroid.domain.model.response.CartActionModel
import com.mk.sepetandroid.domain.model.response.CartModel
import com.mk.sepetandroid.domain.model.response.ProductModel

sealed class SingleProductEvent{
    data class GetProduct(val id : String?) : SingleProductEvent()
    data class AddProduct(val cartActionModel: CartActionModel) : SingleProductEvent()
    data object CloseDialog : SingleProductEvent()
}