package com.mk.sepetandroid.presentation.cart

import android.media.session.MediaSession.Token
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.domain.model.response.AddressModel
import com.mk.sepetandroid.domain.model.response.CartActionModel
import com.mk.sepetandroid.domain.model.response.CartModel
import com.mk.sepetandroid.domain.model.response.ProductModel
import com.mk.sepetandroid.domain.model.response.TokenModel
import com.mk.sepetandroid.domain.use_case.address.GetAddressUseCase
import com.mk.sepetandroid.domain.use_case.cart.DeleteProductCartUseCase
import com.mk.sepetandroid.domain.use_case.cart.GetCartUseCase
import com.mk.sepetandroid.domain.use_case.cart.OrderCartUseCase
import com.mk.sepetandroid.domain.use_case.cart.UpdateCartUseCase
import com.mk.sepetandroid.domain.use_case.product.GetProductsUseCase
import com.mk.sepetandroid.domain.use_case.token.GetTokenUseCase
import com.mk.sepetandroid.domain.use_case.token.UpdateTokenUseCase
import com.mk.sepetandroid.domain.use_case.user.RefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val updateTokenUseCase: UpdateTokenUseCase,
    private val deleteProductCartUseCase: DeleteProductCartUseCase,
    private val getAddressUseCase: GetAddressUseCase,
    private val orderCartUseCase : OrderCartUseCase
): ViewModel() {
    private val _state = mutableStateOf(CartState())
    val state : State<CartState> = _state


    private fun getCart(){
        getToken { it ->
            getCartUseCase.invoke(it.accessToken,false)
                .onEach {
                    when(it){
                        is Resource.Error -> {
                            if (it.responseModel?.status == 401){
                                refreshToken()
                            }else{
                                _state.value = CartState(
                                    isError = true,
                                    message = it.message
                                )
                            }
                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            _state.value = CartState(
                                cart = it.data,
                                address = _state.value.address
                            )
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

    private fun getToken(tokenModel : (TokenModel) -> Unit){
        getTokenUseCase.invoke()
            .onEach {
                when(it){
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        if (it.data.isNotEmpty())
                            tokenModel(it.data.first())
                        else
                            _state.value = CartState(
                                isError = true,
                                message = "If you see cart please login again"
                            )

                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun updateCart(cartActionModel: CartActionModel){
        getToken {
            updateCartUseCase
                .invoke(it.accessToken, cartActionModel)
                .onEach {
                    when(it){
                        is Resource.Error -> {

                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            _state.value = CartState(

                            )
                            onEvent(CartEvent.GetCart)
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

    private fun refreshToken(){
        getToken { token ->
            refreshTokenUseCase
                .invoke(token.refreshToken)
                .onEach {
                    when(it){
                        is Resource.Error -> {}
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            updateTokenUseCase.invoke(
                                TokenModel(
                                    token.id,
                                    it.data.accessToken,
                                    it.data.refreshToken
                                )
                            )
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

    private fun deleteProduct(id : String){
        getToken {
            println(id)
            deleteProductCartUseCase.invoke(it.accessToken,id)
                .onEach {
                    when(it){
                        is Resource.Error -> {}
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            onEvent(CartEvent.GetCart)
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

    private fun getAddress(){
        getToken {
            getAddressUseCase.invoke(it.accessToken)
                .onEach {
                    when(it){
                        is Resource.Error -> {

                            _state.value = CartState(
                                isError = true,
                                message = it.message
                            )
                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            _state.value = CartState(
                                cart = _state.value.cart,
                                address = it.data
                            )
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

    private fun placeOrder(){
        getToken {
            orderCartUseCase.invoke(it.accessToken)
                .onEach {
                    when(it){
                        is Resource.Error -> {

                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            _state.value = CartState(
                                isSuccess = true,
                                message = it.data
                            )
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }


    fun onEvent(cartEvent: CartEvent){
        when(cartEvent){
            is CartEvent.GetCart -> {
                getCart()
                getAddress()
            }

            is CartEvent.DeleteProduct -> {
                deleteProduct(cartEvent.id)
            }

            is CartEvent.UpdateCart -> {
                updateCart(cartEvent.cartActionModel)
            }

            CartEvent.PlaceOrder -> {
                placeOrder()
            }
        }
    }

}