package com.mk.sepetandroid.domain.use_case.user

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.toDto
import com.mk.sepetandroid.domain.model.user.ProfileModel
import com.mk.sepetandroid.domain.repository.IUserRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val iUserRepo: IUserRepo
) {
    fun invoke(profileModel: ProfileModel, accessToken : String) = flow<Resource<String>>{
        emit(Resource.Loading())
        iUserRepo.putProfile(profileModel.toDto(), accessToken)
        emit(Resource.Success("Profile updated"))
    }.catchHandler {
        emit(Resource.Error(it.message,it.responseModel))
    }
}