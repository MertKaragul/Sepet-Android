package com.mk.sepetandroid.presentation.signin

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.sepetandroid.R
import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.domain.model.response.TokenModel
import com.mk.sepetandroid.domain.model.user.ProfileModel
import com.mk.sepetandroid.domain.model.view.DialogModel
import com.mk.sepetandroid.domain.use_case.token.AddTokenUseCase
import com.mk.sepetandroid.domain.use_case.user.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val addTokenUseCase: AddTokenUseCase
) : ViewModel() {
    private val _state = mutableStateOf(SignInState())
    val state : State<SignInState> = _state


    private fun signIn(profileModel: ProfileModel){
        signInUseCase.invoke(profileModel)
            .onEach {
                when(it){
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            status = true,
                            dialogModel = DialogModel(
                                icon = R.drawable.baseline_check_circle_24,
                                title = "Error",
                                description = it.message ?: "Something went wrong, please try again"
                            )
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            status = true,
                            dialogModel = DialogModel(
                                icon = R.drawable.baseline_check_circle_24,
                                title = "Successfully logged in"
                            )
                        )

                        saveToken(it.data)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun saveToken(tokenModel: TokenModel){
        addTokenUseCase.invoke(tokenModel)
            .onEach {
                when(it){
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        _state.value = SignInState(
                            successSignIn = true
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }


    fun onEvent(event : SignInEvent){
        when(event){
            is SignInEvent.SignIn -> {
                signIn(event.profileModel)
            }

            is SignInEvent.CloseDialog -> {
                _state.value = state.value.copy(
                    status = false
                )
            }
        }
    }
}