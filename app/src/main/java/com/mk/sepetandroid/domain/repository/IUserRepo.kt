package com.mk.sepetandroid.domain.repository

import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.data.remote.dto.TokenDto
import com.mk.sepetandroid.data.remote.dto.ProfileDto

interface IUserRepo {
    suspend fun login(profileDto: ProfileDto) : TokenDto
    suspend fun register(profileDto: ProfileDto) : ResponseDto
    suspend fun profile(authHeader : String) : ProfileDto
    suspend fun putProfile(profileDto: ProfileDto, authHeader : String) : ResponseDto
    suspend fun refresh(refreshToken : String) : TokenDto
}