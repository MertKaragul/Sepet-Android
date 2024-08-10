package com.mk.sepetandroid.presentation.signin

import com.mk.sepetandroid.domain.model.view.DialogModel

data class SignInState(
    val status: Boolean = false,
    val message: String = "",
    val dialogModel: DialogModel? = null,
    val isLoading: Boolean = false,
    val successSignIn : Boolean = false
)
