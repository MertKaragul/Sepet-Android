package com.mk.sepetandroid.presentation.address

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.domain.model.response.AddressModel
import com.mk.sepetandroid.domain.model.response.TokenModel
import com.mk.sepetandroid.domain.use_case.address.AddAddressUseCase
import com.mk.sepetandroid.domain.use_case.address.DeleteAddressUseCase
import com.mk.sepetandroid.domain.use_case.address.GetAddressUseCase
import com.mk.sepetandroid.domain.use_case.address.UpdateAddressUseCase
import com.mk.sepetandroid.domain.use_case.token.GetTokenUseCase
import com.mk.sepetandroid.domain.use_case.user.RefreshTokenUseCase
import com.mk.sepetandroid.presentation.profile.ProfileEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val getAddressUseCase: GetAddressUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val addressUseCase: AddAddressUseCase,
    private val deleteAddressUseCase: DeleteAddressUseCase,
    private val updateAddressUseCase: UpdateAddressUseCase
) : ViewModel() {
    private val _state = mutableStateOf(AddressState())
    val state : State<AddressState> = _state


    private fun getAddress(){
        getToken { token ->
            getAddressUseCase.invoke(token.accessToken)
                .onEach {
                    when(it){
                        is Resource.Error -> {
                            _state.value = AddressState(
                                isError = true,
                                message = it.responseModel?.message ?: "Something went wrong"
                            )

                        }
                        is Resource.Loading -> {
                            _state.value = AddressState(
                                isLoading = true
                            )
                        }
                        is Resource.Success -> {
                            _state.value = AddressState(addressList = it.data)
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

    private fun getToken(token : (TokenModel) -> Unit){
        getTokenUseCase.invoke()
            .onEach {
                when(it){
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        token(it.data.first())
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun refreshToken(tokenModel: TokenModel){
        refreshTokenUseCase.invoke(tokenModel.refreshToken)
            .onEach {

            }.launchIn(viewModelScope)
    }

    private fun addAddress(addressModel: AddressModel){
        getToken {
            addressUseCase.invoke(it.accessToken, addressModel)
                .onEach {
                    when(it){
                        is Resource.Error -> {
                            _state.value = AddressState(
                                isError = true,
                                message = it.message
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(
                                isLoading = true
                            )
                        }
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                isSuccess = true,
                                message = "Address added"
                            )
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

    private fun deleteAddress(addressModel: AddressModel){
        getToken {
            deleteAddressUseCase.invoke(it.accessToken, addressModel)
                .onEach {
                    when(it){
                        is Resource.Error -> {
                            _state.value = AddressState(
                                isError = true,
                                message = it.message
                            )
                        }

                        is Resource.Loading -> {
                            _state.value = AddressState(
                                isLoading = true
                            )
                        }
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                isSuccess = true,
                                message = "Address deleted"
                            )
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

    private fun updateAddress(addressModel: AddressModel){
        getToken {
            updateAddressUseCase.invoke(it.accessToken,addressModel)
                .onEach {
                    when(it){
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                isError = true,
                                message = it.message
                            )
                        }

                        is Resource.Loading -> {
                            _state.value = _state.value.copy(
                                isLoading = true
                            )
                        }
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                isSuccess = true,
                                message = "Address updated"
                            )
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }


    fun onEvent(addressEvent: AddressEvent){
        when(addressEvent){
            is AddressEvent.GetAddress -> {
                getAddress()
            }

            is AddressEvent.AddAddress -> {
                addAddress(addressEvent.addressModel)
            }

            is AddressEvent.DeleteAddress -> {
                deleteAddress(addressEvent.addressModel)
            }

            is AddressEvent.UpdateAddress -> {
                updateAddress(addressEvent.addressModel)
            }

            AddressEvent.CloseDialog -> {
                _state.value = _state.value.copy(
                    isError = false,
                    isLoading = false,
                    isSuccess = false,
                    message = ""
                )

                onEvent(
                    AddressEvent.GetAddress
                )
            }
        }
    }
}