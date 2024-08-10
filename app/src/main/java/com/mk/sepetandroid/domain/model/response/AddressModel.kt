package com.mk.sepetandroid.domain.model.response

import com.google.gson.annotations.SerializedName

data class AddressModel(
    @SerializedName("_id")
    val id : String ?= "",
    val name : String? = "",
    val address : String ?= "",
    val postCode : String ?= "",
    val apartment : String ?= ""
)
