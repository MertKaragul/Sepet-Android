package com.mk.sepetandroid.domain.model.view

import com.mk.sepetandroid.domain.model.response.ResponseModel

data class DialogModel(
    val icon : Int,
    val title : String,
    val description : String?= null,
    val response : ResponseModel? = null,
    val messages : List<Any>? = null
)