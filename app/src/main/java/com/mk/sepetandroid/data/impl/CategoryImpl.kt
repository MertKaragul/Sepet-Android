package com.mk.sepetandroid.data.impl

import com.mk.sepetandroid.data.remote.CategoryApi
import com.mk.sepetandroid.data.remote.dto.CategoryDto
import com.mk.sepetandroid.domain.repository.ICategoryRepo
import javax.inject.Inject

class CategoryImpl @Inject constructor(
    private val categoryApi: CategoryApi
) : ICategoryRepo {
    override suspend fun getCategories(id : String?): List<CategoryDto> {
        return categoryApi.getCategories(id)
    }
}