package com.mk.sepetandroid.domain.model.response

data class ResponseModel(
    val message : String,
    val status : Int,
    val messages : List<String>?= null
)
