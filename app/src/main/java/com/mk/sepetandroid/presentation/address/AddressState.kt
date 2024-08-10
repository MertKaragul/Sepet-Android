package com.mk.sepetandroid.presentation.address

import androidx.core.app.NotificationCompat.MessagingStyle.Message
import com.mk.sepetandroid.domain.model.response.AddressModel

data class AddressState(
    val isSuccess : Boolean = false,
    val isLoading : Boolean = false,
    val isError : Boolean = false,
    val message: String ?= null,
    val addressList : List<AddressModel> = emptyList()

)
