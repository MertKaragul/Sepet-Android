package com.mk.sepetandroid.presentation.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.sepetandroid.R
import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.domain.model.user.ProfileModel
import com.mk.sepetandroid.domain.model.view.DialogModel
import com.mk.sepetandroid.domain.use_case.user.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
): ViewModel() {
    private val _state = mutableStateOf(SignUpState())
    val state : State<SignUpState> = _state

    private fun signUp(profileModel: ProfileModel){
        println(profileModel)
        signUpUseCase.invoke(profileModel)
            .onEach {
                when(it){
                    is Resource.Loading -> {
                        _state.value = SignUpState(
                            isLoading = true
                        )
                    }

                    is Resource.Error -> {
                        _state.value = SignUpState(
                            isError = true,
                        )
                    }

                    is Resource.Success -> {
                        _state.value = SignUpState(
                            isError = true,
                            dialogModel = DialogModel(
                                R.drawable.baseline_check_circle_24,
                                "Successfully",
                                "You successfully sign up",
                            )
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun onEvent(onEvent: SignUpEvent){
        when(onEvent){
            is SignUpEvent.SignUp -> {
                signUp(onEvent.profileModel)
            }

            is SignUpEvent.CloseDialog -> {
                _state.value = SignUpState(
                    isError = false,
                    dialogModel = null
                )
            }
        }
    }

}