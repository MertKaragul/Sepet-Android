package com.mk.sepetandroid.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.domain.model.enums.RouterEnum
import com.mk.sepetandroid.domain.model.response.TokenModel
import com.mk.sepetandroid.domain.use_case.token.DeleteAllUseCase
import com.mk.sepetandroid.domain.use_case.token.DeleteTokenUseCase
import com.mk.sepetandroid.domain.use_case.token.GetTokenUseCase
import com.mk.sepetandroid.domain.use_case.token.UpdateTokenUseCase
import com.mk.sepetandroid.domain.use_case.user.GetProfileUseCase
import com.mk.sepetandroid.domain.use_case.user.RefreshTokenUseCase
import com.mk.sepetandroid.presentation.route.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val updateTokenUseCase: UpdateTokenUseCase,
    private val deleteAllUseCase: DeleteAllUseCase,
) : ViewModel(){
    private val _state = mutableStateOf(ProfileState())
    val state : State<ProfileState> = _state

    init{
        onEvent(ProfileEvent.GetProfile)
    }

    private fun getProfile(){
        getTokenUseCase.invoke()
            .onEach {
                when(it){
                    is Resource.Loading -> {
                        _state.value = ProfileState(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = ProfileState(
                            routePage = if (it.data.isEmpty()) RouterEnum.SIGN_IN else null
                        )
                        getProfileApi(it.data.firstOrNull())
                    }

                    is Resource.Error -> {
                        _state.value = ProfileState(
                            routePage = RouterEnum.SIGN_IN
                        )
                        deleteAllUseCase.invoke().launchIn(viewModelScope)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun getProfileApi(tokenModel: TokenModel?){
        if (tokenModel == null)
            return


        getProfileUseCase.invoke(tokenModel.accessToken)
            .onEach {
                when(it){
                    is Resource.Error -> {
                        _state.value = ProfileState(
                            isError = true,
                            message = it.message
                        )

                        refreshToken(tokenModel)
                    }
                    is Resource.Loading -> {
                        _state.value = ProfileState(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = ProfileState(
                            profile = it.data
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun refreshToken(tokenModel: TokenModel){
        refreshTokenUseCase.invoke(tokenModel.refreshToken)
            .onEach {
                when(it){
                    is Resource.Error -> {
                        _state.value = ProfileState(
                            isError = true,
                            message = "You can retry logged in",
                            routePage = RouterEnum.SIGN_IN
                        )
                        deleteAllUseCase.invoke().launchIn(viewModelScope)
                    }
                    is Resource.Loading -> {
                        _state.value = ProfileState(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        val updatedTokenModel = TokenModel(
                            id = tokenModel.id,
                            accessToken = it.data.accessToken,
                            refreshToken = it.data.refreshToken
                        )
                        updateTokenUseCase.invoke(updatedTokenModel).launchIn(viewModelScope)
                        onEvent(ProfileEvent.GetProfile)
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun onEvent(profileEvent: ProfileEvent){
        when(profileEvent){
            is ProfileEvent.GetProfile -> {
                getProfile()
            }
        }
    }
}