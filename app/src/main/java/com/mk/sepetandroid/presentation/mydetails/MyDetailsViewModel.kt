package com.mk.sepetandroid.presentation.mydetails

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.domain.model.response.TokenModel
import com.mk.sepetandroid.domain.model.user.ProfileModel
import com.mk.sepetandroid.domain.use_case.token.GetTokenUseCase
import com.mk.sepetandroid.domain.use_case.token.UpdateTokenUseCase
import com.mk.sepetandroid.domain.use_case.user.GetProfileUseCase
import com.mk.sepetandroid.domain.use_case.user.RefreshTokenUseCase
import com.mk.sepetandroid.domain.use_case.user.UpdateUserUseCase
import com.mk.sepetandroid.presentation.single_product.SingleProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MyDetailsViewModel @Inject constructor(
    private val updateUserUseCase : UpdateUserUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val updateTokenModel: UpdateTokenUseCase,
    private val getProfileUseCase: GetProfileUseCase
) : ViewModel() {
    private val _state = mutableStateOf(MyDetailsState())
    val state : State<MyDetailsState> = _state

    private fun updateProfile(profileModel: ProfileModel){
        getToken {
            updateUserUseCase.invoke(profileModel,it.accessToken)
                .onEach {
                    when(it){
                        is Resource.Error -> {
                            if (it.responseModel?.status == 401)
                                refreshToken()
                            else
                                _state.value = MyDetailsState(
                                    isError = true,
                                    message = it.responseModel?.messages?.first() ?: "Something went wrong"
                                )
                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            _state.value = MyDetailsState(
                                isSuccess = true,
                                message = "Profile successfully updated"
                            )
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

    private fun getProfile(){
        getToken {
            getProfileUseCase.invoke(it.accessToken)
                .onEach {
                    when(it){
                        is Resource.Error -> {

                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            _state.value = MyDetailsState(
                                profileModel = it.data
                            )
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
                        if (it.data.isNotEmpty())
                            token(it.data.first())
                        else
                            _state.value = MyDetailsState(
                                isError = true,
                                message = "Just only authorized user updated profile"
                            )
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun refreshToken(){
        getToken {
            refreshTokenUseCase.invoke(it.refreshToken)
                .onEach { ref ->
                    when(ref){
                        is Resource.Error -> {

                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            updateTokenModel.invoke(
                                TokenModel(
                                    ref.data.id,
                                    it.accessToken,
                                    it.refreshToken
                                )
                            )
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

    fun onEvent(myDetailsEvent: MyDetailsEvent){
        when(myDetailsEvent){
            is MyDetailsEvent.UpdateProfile -> {
                updateProfile(myDetailsEvent.profileModel)
            }

            MyDetailsEvent.GetProfile -> {
                getProfile()
            }

            MyDetailsEvent.CloseDialog -> {
                _state.value = MyDetailsState(
                    isError = false,
                    isSuccess = false,
                    message = _state.value.message
                )
            }
        }
    }
}