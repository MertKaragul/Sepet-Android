package com.mk.sepetandroid.common

import com.mk.sepetandroid.domain.model.response.ResponseModel

sealed class Resource<T> {
    class Loading<T> : Resource<T>()
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(
        val message: String?= null,
        val responseModel: ResponseModel?= null,
    ) : Resource<T>()
}