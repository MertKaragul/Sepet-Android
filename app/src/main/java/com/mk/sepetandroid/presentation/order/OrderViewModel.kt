package com.mk.sepetandroid.presentation.order

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.annotations.Until
import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.domain.model.response.TokenModel
import com.mk.sepetandroid.domain.use_case.cart.GetCartUseCase
import com.mk.sepetandroid.domain.use_case.token.GetTokenUseCase
import com.mk.sepetandroid.domain.use_case.user.RefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val getCartUseCase: GetCartUseCase
) : ViewModel() {
    private val _state = mutableStateOf(OrderState())
    val state : State<OrderState> = _state


    private fun getOrders(){
        getToken {
            getCartUseCase.invoke(it.accessToken,true)
                .onEach {
                    when(it){
                        is Resource.Error -> {

                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            println(it.data)
                            _state.value = OrderState(
                                orders = it.data
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
                        token(it.data.first())
                    }
                }
            }
            .launchIn(viewModelScope)
    }


    fun onEvent(orderEvent: OrderEvent){
        when(orderEvent){
            is OrderEvent.GetOrders -> {
                getOrders()
            }

            OrderEvent.CloseDialog -> {

            }
        }
    }
}