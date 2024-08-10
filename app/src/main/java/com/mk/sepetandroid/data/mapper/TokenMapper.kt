package com.mk.sepetandroid.data.mapper

import com.mk.sepetandroid.data.local.entity.TokenEntity
import com.mk.sepetandroid.data.remote.dto.TokenDto
import com.mk.sepetandroid.domain.model.response.TokenModel

fun TokenDto.toModel() = TokenModel(0,this.accessToken,this.refreshToken)
fun TokenEntity.toModel() = TokenModel(this.id,this.accessToken,this.refreshToken)
fun TokenModel.toEntity() = TokenEntity(0,this.accessToken,this.refreshToken)
