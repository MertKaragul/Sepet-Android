package com.mk.sepetandroid.domain.use_case.product

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.toProductModel
import com.mk.sepetandroid.domain.model.response.ProductModel
import com.mk.sepetandroid.domain.repository.IProductRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepo : IProductRepo
) {
    fun invoke(
        min : Int?= null,
        max : Int?= null,
        category : List<String> = emptyList(),
        id : List<String> = emptyList(),
    ) = flow<Resource<List<ProductModel>>>{
        emit(Resource.Loading())
        emit(Resource.Success(productRepo.getProducts(min,max,category,id).map { it.toProductModel() }))
    }.catchHandler {
        emit(Resource.Error(it.message))
    }

}