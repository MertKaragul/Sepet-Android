package com.mk.sepetandroid.data.impl

import com.mk.sepetandroid.data.remote.ProductApi
import com.mk.sepetandroid.data.remote.dto.ProductDto
import com.mk.sepetandroid.domain.repository.IProductRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductImpl @Inject constructor(
    private val productApi: ProductApi,
) : IProductRepo {

    override suspend fun getProducts(min: Int?, max: Int?, category: List<String>, id : List<String>): List<ProductDto> {
        return productApi.getProducts(
            min, max, category, id
        )
    }
}