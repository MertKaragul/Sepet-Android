package com.mk.sepetandroid.data.mapper

import com.mk.sepetandroid.data.remote.dto.ProductDto
import com.mk.sepetandroid.domain.model.response.ProductModel

fun ProductDto.toProductModel() = ProductModel(
    id,name, description, price, image, discountPrice, discountStatus
)

fun ProductModel.toDto() = ProductDto(
    id, name, description, price, image, discountPrice, discountStatus
)
