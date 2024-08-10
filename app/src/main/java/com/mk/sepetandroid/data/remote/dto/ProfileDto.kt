package com.mk.sepetandroid.data.remote.dto

data class ProfileDto(
    val username : String?=null,
    val email : String ?= null,
    val password : String ?= null,
    val passwordAgain : String ?= null,
    val address: String?= null,
    val phone : String?= null
)
