package com.mk.sepetandroid.data.mapper

import com.mk.sepetandroid.data.remote.dto.ProfileDto
import com.mk.sepetandroid.domain.model.user.ProfileModel

fun ProfileDto.toModel() = ProfileModel(username = this.username, email = this.email, password = this.password, passwordAgain =  this.passwordAgain, address = this.address, phone = this.phone)
fun ProfileModel.toDto() = ProfileDto(username = this.username, email = this.email, password = this.password, passwordAgain =  this.passwordAgain, address = this.address, phone = this.phone)