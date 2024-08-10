package com.mk.sepetandroid.domain.use_case.user

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.data.mapper.toModel
import com.mk.sepetandroid.domain.model.user.ProfileModel
import com.mk.sepetandroid.domain.repository.IUserRepo
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val userRepo : IUserRepo
) {
    fun invoke(authHeader : String) = flow<Resource<ProfileModel>>{
        emit(Resource.Loading())
        emit(Resource.Success(userRepo.profile(authHeader).toModel()))
    }.catch {
        emit(Resource.Error(it.localizedMessage))
    }

}