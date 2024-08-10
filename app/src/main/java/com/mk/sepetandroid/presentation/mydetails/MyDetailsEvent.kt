package com.mk.sepetandroid.presentation.mydetails

import com.mk.sepetandroid.domain.model.user.ProfileModel

sealed class MyDetailsEvent{
    data class UpdateProfile(val profileModel: ProfileModel) : MyDetailsEvent()
    data object GetProfile : MyDetailsEvent()
    data object CloseDialog : MyDetailsEvent()
}