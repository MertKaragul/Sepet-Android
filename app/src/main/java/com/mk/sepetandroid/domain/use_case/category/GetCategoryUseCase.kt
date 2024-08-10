package com.mk.sepetandroid.domain.use_case.category

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.toModel
import com.mk.sepetandroid.domain.model.response.CategoryModel
import com.mk.sepetandroid.domain.repository.ICategoryRepo
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val iCategoryRepo: ICategoryRepo
) {
    fun invoke(id : String?) = flow<Resource<List<CategoryModel>>>{
        emit(Resource.Loading())
        emit(Resource.Success(iCategoryRepo.getCategories(id).map { it.toModel() }))
    }.catchHandler {
        emit(Resource.Error(it.message))
    }
}