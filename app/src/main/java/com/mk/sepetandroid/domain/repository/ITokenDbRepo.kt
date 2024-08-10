package com.mk.sepetandroid.domain.repository

import com.mk.sepetandroid.data.local.entity.TokenEntity
import com.mk.sepetandroid.data.remote.dto.TokenDto

interface ITokenDbRepo {
    suspend fun addToken(token: TokenEntity)
    suspend fun deleteToken(token: TokenEntity)
    suspend fun updateToken(token: TokenEntity)
    suspend fun getTokens(): List<TokenEntity>
    suspend fun deleteAll()
}