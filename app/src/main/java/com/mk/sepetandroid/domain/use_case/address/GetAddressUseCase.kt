package com.mk.sepetandroid.domain.use_case.address

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.data.mapper.toModel
import com.mk.sepetandroid.domain.model.response.AddressModel
import com.mk.sepetandroid.domain.repository.IAddressRepo
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAddressUseCase @Inject constructor(
    private val iAddressRepo: IAddressRepo
) {

    fun invoke(accessToken : String) = flow<Resource<List<AddressModel>>>{
        emit(Resource.Loading())
        val response = iAddressRepo.getAddress(accessToken).map { it.toModel() }
        emit(Resource.Success(response))
    }.catch{
        emit(Resource.Error(it.localizedMessage))
    }
}