package com.mk.sepetandroid.domain.model.form

data class SignUpModel (
    val name : String,
    val email : String,
    val password : String,
    val rePassword: String,
    val address : String,
    val phoneNumber : String
)