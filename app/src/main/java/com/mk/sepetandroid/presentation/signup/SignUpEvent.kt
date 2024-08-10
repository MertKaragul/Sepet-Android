package com.mk.sepetandroid.presentation.signup

import com.mk.sepetandroid.domain.model.user.ProfileModel

sealed class SignUpEvent {
    data class SignUp(val profileModel: ProfileModel) : SignUpEvent()
    data object CloseDialog : SignUpEvent()
}