package com.mk.sepetandroid.data.remote.dto

data class ResponseDto(
    val message : String,
    val status : Int,
    val messages : List<String>?= null
)
