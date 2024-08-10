package com.mk.sepetandroid.data.impl

import com.mk.sepetandroid.data.remote.UserApi
import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.data.remote.dto.TokenDto
import com.mk.sepetandroid.data.remote.dto.ProfileDto
import com.mk.sepetandroid.domain.repository.IUserRepo
import javax.inject.Inject

class UserImpl @Inject constructor(
    private val userApi: UserApi
) : IUserRepo {

    override suspend fun login(profileDto: ProfileDto): TokenDto {
        return userApi.login(profileDto)
    }

    override suspend fun register(profileDto: ProfileDto): ResponseDto {
        return userApi.register(profileDto)
    }

    override suspend fun profile(authHeader: String): ProfileDto {
        return userApi.profile(authHeader)
    }

    override suspend fun putProfile(profileDto: ProfileDto, authHeader: String): ResponseDto {
        return userApi.putProfile(profileDto,authHeader)
    }

    override suspend fun refresh(refreshToken: String): TokenDto {
        return userApi.refresh(refreshToken)
    }

}