package com.mk.sepetandroid.data.mapper

import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.domain.model.response.ResponseModel

fun ResponseDto.toModel() = ResponseModel(this.message,this.status,this.messages)
fun ResponseModel.toDto() = ResponseDto(this.message,this.status,this.messages)