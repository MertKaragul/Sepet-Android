package com.mk.sepetandroid.domain.model.response

data class TokenModel(
    val id : Int?=null,
    val accessToken : String,
    val refreshToken : String
)
