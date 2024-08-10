package com.mk.sepetandroid.domain.model.user

data class ProfileModel(
    val username : String?= null,
    val email : String?=null,
    val password : String ?= null,
    val passwordAgain : String?=null,
    val address: String?= null,
    val phone : String?= null
)
