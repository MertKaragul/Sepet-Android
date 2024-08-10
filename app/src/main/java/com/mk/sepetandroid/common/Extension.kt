package com.mk.sepetandroid.common

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mk.sepetandroid.data.mapper.toModel
import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.domain.model.response.ResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import java.io.IOException


fun <T> Flow<T>.catchHandler(action: suspend FlowCollector<T>.(Resource.Error<String>) -> Unit): Flow<T> {
    return catch {
        when (it) {
            is retrofit2.HttpException -> {
                try{
                    // HTTP exceptions
                    val error = it.response()?.errorBody()?.string()
                    val type = object : TypeToken<ResponseModel>(){}.type
                    val responseError = Gson().fromJson<ResponseModel>(error.toString(), type)
                    action(Resource.Error(message = responseError.message, responseModel = ResponseModel(responseError.message, responseError.status, responseError.messages)))
                }catch (e:Exception){
                    action(Resource.Error(message = "Something went wrong", responseModel = ResponseModel("Something went wrong", 500)))
                }
            }
            is IOException -> {
                // Network exceptions
                action(Resource.Error(message = "Network error"))
            }
            else -> {
                // Other exceptions
                action(Resource.Error(message = it.localizedMessage ?: "Something went wrong"))
            }
        }
    }
}
