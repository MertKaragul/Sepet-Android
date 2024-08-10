package com.mk.sepetandroid.domain.use_case.user

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.toDto
import com.mk.sepetandroid.domain.model.user.ProfileModel
import com.mk.sepetandroid.domain.repository.IUserRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val iUserRepo: IUserRepo
) {
    fun invoke(profileModel: ProfileModel) = flow<Resource<String>>{
        emit(Resource.Loading())
        val res = iUserRepo.register(profileModel.toDto())
        println(res)
        emit(Resource.Success("Successfully sign up"))
    }.catchHandler{
        println("catch handler work !")
        emit(it)
    }
}