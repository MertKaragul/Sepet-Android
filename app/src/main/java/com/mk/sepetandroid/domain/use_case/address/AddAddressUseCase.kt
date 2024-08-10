package com.mk.sepetandroid.domain.use_case.address

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.data.mapper.toDto
import com.mk.sepetandroid.domain.model.response.AddressModel
import com.mk.sepetandroid.domain.repository.IAddressRepo
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddAddressUseCase @Inject constructor(
    private val iAddressRepo: IAddressRepo
) {
    fun invoke(accessToken : String, addressModel: AddressModel) = flow<Resource<String>> {
        emit(Resource.Loading())
        println("${accessToken},${addressModel.toDto()}")
        iAddressRepo.addAddress(accessToken,addressModel.toDto())
        emit(Resource.Success("Address successfully added"))
    }.catch {
        emit(Resource.Error(it.localizedMessage))
    }
}