package com.mk.sepetandroid.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AddressDto(
    @SerializedName("_id")
    val id : String ?= "",
    val name : String? = "",
    val address : String ?= "",
    val postCode : String ?= "",
    val apartment : String ?= ""
)
