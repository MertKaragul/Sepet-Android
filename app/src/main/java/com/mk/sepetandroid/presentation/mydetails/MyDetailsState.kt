package com.mk.sepetandroid.presentation.mydetails

import com.mk.sepetandroid.domain.model.user.ProfileModel

data class MyDetailsState(
    val isError : Boolean = false,
    val message : String = "",
    val isSuccess : Boolean = false,
    val profileModel: ProfileModel ?= null
)