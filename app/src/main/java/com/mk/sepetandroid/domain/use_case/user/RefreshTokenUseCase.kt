package com.mk.sepetandroid.domain.use_case.user

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.data.mapper.toModel
import com.mk.sepetandroid.domain.model.response.TokenModel
import com.mk.sepetandroid.domain.repository.IUserRepo
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val iUserRepo: IUserRepo
) {
    fun invoke(refreshToken : String) = flow<Resource<TokenModel>>{
        emit(Resource.Loading())
        emit(Resource.Success(iUserRepo.refresh(refreshToken).toModel()))
    }.catch {
        emit(Resource.Error(it.localizedMessage))
    }
}