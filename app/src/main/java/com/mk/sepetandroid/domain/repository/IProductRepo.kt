package com.mk.sepetandroid.domain.repository

import com.mk.sepetandroid.data.remote.dto.ProductDto
import kotlinx.coroutines.flow.Flow

interface IProductRepo {
    suspend fun getProducts(
        min : Int?= null,
        max : Int?= null,
        category : List<String> = emptyList(),
        id : List<String> = emptyList()
    ) : List<ProductDto>
}