package com.mk.sepetandroid.data.mapper

import com.mk.sepetandroid.data.remote.dto.CategoryDto
import com.mk.sepetandroid.domain.model.response.CategoryModel

fun CategoryDto.toModel() = CategoryModel(this.id,this.name,this.image)
fun CategoryModel.toDto() = CategoryDto(this.id,this.name,this.image)