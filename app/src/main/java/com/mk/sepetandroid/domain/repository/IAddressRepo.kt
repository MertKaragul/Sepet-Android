package com.mk.sepetandroid.domain.repository

import com.mk.sepetandroid.data.remote.dto.AddressDto

interface IAddressRepo {
    suspend fun getAddress(accessToken : String) : List<AddressDto>
    suspend fun updateAddress(accessToken : String,addressDto: AddressDto)
    suspend fun deleteAddress(accessToken : String, id : String)
    suspend fun addAddress(accessToken : String,addressDto: AddressDto)
}