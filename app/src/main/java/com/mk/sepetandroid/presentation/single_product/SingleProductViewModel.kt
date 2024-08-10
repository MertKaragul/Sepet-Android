package com.mk.sepetandroid.presentation.single_product

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.sepetandroid.R
import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.data.mapper.toDto
import com.mk.sepetandroid.domain.model.response.CartActionModel
import com.mk.sepetandroid.domain.model.response.CartModel
import com.mk.sepetandroid.domain.model.response.ProductModel
import com.mk.sepetandroid.domain.model.response.TokenModel
import com.mk.sepetandroid.domain.model.view.DialogModel
import com.mk.sepetandroid.domain.use_case.cart.AddToCartUseCase
import com.mk.sepetandroid.domain.use_case.product.GetProductsUseCase
import com.mk.sepetandroid.domain.use_case.token.GetTokenUseCase
import com.mk.sepetandroid.domain.use_case.token.UpdateTokenUseCase
import com.mk.sepetandroid.domain.use_case.user.RefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SingleProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val updateTokenUseCase: UpdateTokenUseCase
) : ViewModel() {
    private val _state = mutableStateOf(SingleProductState())
    val state : State<SingleProductState> = _state


    private fun getProduct(id : String?){
        if (id == null){
            _state.value = SingleProductState(
                isError = true,
                message = "Product not found"
            )
            return
        }
        getProductsUseCase.invoke(id = listOf(id))
            .onEach {
                when(it){
                    is Resource.Success -> {
                        _state.value = SingleProductState(
                            productModel = it.data.first(),
                        )
                    }
                    is Resource.Error -> {
                        _state.value = SingleProductState(
                            isError = true,
                            message = it.message
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = SingleProductState(
                            isLoading = true
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }


    private fun getToken(token : (TokenModel) -> Unit){
        getTokenUseCase.invoke()
            .onEach {
                when(it){
                    is Resource.Error -> {
                        _state.value = SingleProductState(
                            isError = true,
                            message = "Only authenticated users addable products to cart"
                        )
                    }
                    is Resource.Loading -> {}

                    is Resource.Success -> {
                        if (it.data.isNotEmpty())
                            token(it.data.first())
                        else
                            _state.value = SingleProductState(
                                isError = true,
                                message = "Just authorized users addable products to cart",
                                productModel = _state.value.productModel
                            )

                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun addProduct(cartActionModel: CartActionModel){
        getToken {
            addToCartUseCase.invoke(it.accessToken, cartActionModel)
                .onEach {
                    when(it){
                        is Resource.Error -> {
                            if (it.responseModel?.status == 401){
                                refreshToken()
                            }else{
                                _state.value = SingleProductState(
                                    isError = true,
                                    message = it.message,
                                )
                            }
                        }
                        is Resource.Loading -> {}

                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                isError = false,
                                isSuccess = true,
                                message = "Product successfully added to cart",
                                dialogModel = DialogModel(
                                    icon = R.drawable.baseline_check_circle_24,
                                    title = "Product successfully added"
                                ),
                            )
                        }
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    private fun refreshToken(){
        getToken { token ->
            refreshTokenUseCase.invoke(token.refreshToken)
                .onEach {
                    when(it){
                        is Resource.Error -> {

                        }
                        is Resource.Loading -> {

                        }
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

    private fun closeDialog(){
        _state.value = SingleProductState(
            isError = false,
            isLoading = false,
            isSuccess = false,
            productModel = _state.value.productModel
        )
    }

    fun onEvent(event : SingleProductEvent){
        when(event){
            is SingleProductEvent.GetProduct -> {
                getProduct(event.id)
            }

            is SingleProductEvent.AddProduct -> {
                addProduct(event.cartActionModel)
            }

            is SingleProductEvent.CloseDialog -> {
                closeDialog()

            }
        }
    }

}