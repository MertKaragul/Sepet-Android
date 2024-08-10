package com.mk.sepetandroid.domain.repository

import com.mk.sepetandroid.data.remote.dto.CategoryDto

interface ICategoryRepo {
    suspend fun getCategories(id : String?) : List<CategoryDto>
}