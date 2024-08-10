package com.mk.sepetandroid.domain.use_case.cart

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.toDto
import com.mk.sepetandroid.data.remote.dto.CartDto
import com.mk.sepetandroid.domain.model.response.CartActionModel
import com.mk.sepetandroid.domain.model.response.CartModel
import com.mk.sepetandroid.domain.repository.ICartRepo
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val iCartRepo: ICartRepo
) {
    fun invoke(accessToken : String, cartActionModel: CartActionModel) = flow<Resource<String>>{
        emit(Resource.Loading())
        iCartRepo.addToCart(accessToken,cartActionModel.toDto())
        emit(Resource.Success("Product added to cart"))
    }.catchHandler {
        emit(Resource.Error(it.message,it.responseModel))
    }
}