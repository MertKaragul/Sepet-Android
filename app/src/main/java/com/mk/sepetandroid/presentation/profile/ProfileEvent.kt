package com.mk.sepetandroid.presentation.profile

sealed class ProfileEvent {
    data object GetProfile : ProfileEvent()
}