package com.mk.sepetandroid.data.mapper

import com.mk.sepetandroid.data.remote.dto.AddressDto
import com.mk.sepetandroid.domain.model.response.AddressModel

fun AddressModel.toDto() = AddressDto(id, name, address, postCode,apartment)
fun AddressDto.toModel() = AddressModel(id, name, address, postCode,apartment)