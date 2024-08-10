package com.mk.sepetandroid.presentation.cart

import com.mk.sepetandroid.domain.model.response.AddressModel
import com.mk.sepetandroid.domain.model.response.CartActionModel
import com.mk.sepetandroid.domain.model.response.CartModel
import com.mk.sepetandroid.domain.model.response.ProductModel


sealed class CartEvent{
    data object GetCart : CartEvent()
    data class DeleteProduct(val id : String) : CartEvent()
    data class UpdateCart(val cartActionModel: CartActionModel) : CartEvent()
    data object PlaceOrder : CartEvent()
}