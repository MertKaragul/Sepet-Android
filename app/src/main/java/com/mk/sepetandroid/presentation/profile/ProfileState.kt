package com.mk.sepetandroid.presentation.profile

import com.mk.sepetandroid.domain.model.enums.RouterEnum
import com.mk.sepetandroid.domain.model.user.ProfileModel

data class ProfileState(
    val isLoading : Boolean = false,
    val isError : Boolean = false,
    val message : String? = null,
    val routePage : RouterEnum? = null,
    val profile : ProfileModel? = null
)