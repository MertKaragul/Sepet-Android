package com.mk.sepetandroid.data.mapper

import com.mk.sepetandroid.data.remote.dto.CartActionDto
import com.mk.sepetandroid.data.remote.dto.CartDto
import com.mk.sepetandroid.data.remote.dto.CartProductsDto
import com.mk.sepetandroid.domain.model.response.CartActionModel
import com.mk.sepetandroid.domain.model.response.CartModel
import com.mk.sepetandroid.domain.model.response.CartProductsModel

fun CartDto.toModel() = CartModel(id,  products.map { it.toModel() } ,createdDate,total, address?.toModel())
fun CartModel.toDto() = CartDto(id, products.map { it.toDto() },createdDate,total,address?.toDto())

fun CartProductsModel.toDto() = CartProductsDto(product.toDto(),count)
fun CartProductsDto.toModel() = CartProductsModel(product.toProductModel(),count)

fun CartActionModel.toDto() = CartActionDto(productId,count,address)
fun CartActionDto.toModel() = CartActionModel(productId,count,address)