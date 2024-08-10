package com.mk.sepetandroid.domain.use_case.cart

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.toModel
import com.mk.sepetandroid.domain.model.response.CartModel
import com.mk.sepetandroid.domain.repository.ICartRepo
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val iCartRepo: ICartRepo
) {
    fun invoke(accessToken : String, ordered : Boolean) = flow<Resource<List<CartModel>>>{
        emit(Resource.Loading())
        val res = iCartRepo.getCart(accessToken, ordered)
        emit(Resource.Success(res.map { it.toModel() }))
    }.catchHandler {
        emit(Resource.Error(it.message, it.responseModel))
    }
}