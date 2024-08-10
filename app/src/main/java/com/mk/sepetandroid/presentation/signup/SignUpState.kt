package com.mk.sepetandroid.presentation.signup

import com.mk.sepetandroid.domain.model.view.DialogModel

data class SignUpState(
    val isError : Boolean = false,
    val dialogModel: DialogModel? = null,
    val isSuccess : Boolean = false,
    val isLoading : Boolean = false,
)
