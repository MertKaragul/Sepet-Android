package com.mk.sepetandroid.domain.use_case.token

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.toEntity
import com.mk.sepetandroid.data.mapper.toModel
import com.mk.sepetandroid.data.remote.dto.TokenDto
import com.mk.sepetandroid.domain.model.response.TokenModel
import com.mk.sepetandroid.domain.repository.ITokenDbRepo
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val tokenDbRepo: ITokenDbRepo
) {
    fun invoke() = flow<Resource<List<TokenModel>>>{
        emit(Resource.Loading())
        emit(Resource.Success(tokenDbRepo.getTokens().map { it.toModel() }))
    }.catch {
        emit(Resource.Error(it.localizedMessage))
    }
}