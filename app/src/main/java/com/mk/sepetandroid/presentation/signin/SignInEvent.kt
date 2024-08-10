package com.mk.sepetandroid.presentation.signin

import com.mk.sepetandroid.domain.model.user.ProfileModel

sealed class SignInEvent{
    data class SignIn(val profileModel : ProfileModel) : SignInEvent()
    data object CloseDialog : SignInEvent()
}