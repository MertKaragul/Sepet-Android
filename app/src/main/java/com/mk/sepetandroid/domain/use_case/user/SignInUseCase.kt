package com.mk.sepetandroid.domain.use_case.user

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.toDto
import com.mk.sepetandroid.data.mapper.toModel
import com.mk.sepetandroid.domain.model.response.TokenModel
import com.mk.sepetandroid.domain.model.user.ProfileModel
import com.mk.sepetandroid.domain.repository.IUserRepo
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val iUserRepo: IUserRepo
) {
    fun invoke(profileModel: ProfileModel) = flow<Resource<TokenModel>>{
        emit(Resource.Loading())
        val data = iUserRepo.login(profileModel.toDto())
        emit(Resource.Success(data.toModel()))
    }.catchHandler{
        emit(Resource.Error(it.message))
    }
}