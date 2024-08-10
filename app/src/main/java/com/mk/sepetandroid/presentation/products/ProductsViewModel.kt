package com.mk.sepetandroid.presentation.products


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.util.fastForEach
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.domain.model.response.CategoryModel
import com.mk.sepetandroid.domain.use_case.category.GetCategoryUseCase
import com.mk.sepetandroid.domain.use_case.product.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private var getProductsUseCase: GetProductsUseCase,
    private val getCategoryUseCase: GetCategoryUseCase
) : ViewModel() {
    private val _state = mutableStateOf(ProductsState())
    val state : State<ProductsState> = _state

    private fun getProducts(min : Int, max : Int, category : String?= null){
        getProductsUseCase.invoke(min, max, listOf(category ?: ""), emptyList())
            .onEach {
                when(it){
                    is Resource.Error -> {
                        _state.value = ProductsState(
                            isError = true,
                            message = it.message
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = ProductsState(
                            isLoading = true
                        )
                    }

                    is Resource.Success -> {
                        _state.value = ProductsState(
                            isError = false,
                            products = it.data,
                            categories = _state.value.categories,
                            category = category,
                            endSearch = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }
    private fun getCategories(){
        getCategoryUseCase.invoke(null)
            .onEach {
                println(it)
                when(it){
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        val category = it.data.toMutableList()
                        category.add(CategoryModel("","All",""))
                        _state.value = ProductsState(
                            products = _state.value.products,
                            categories = category
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun onEvent(productsEvent: ProductsEvent){
        when(productsEvent){
            is ProductsEvent.GetProducts -> {
                getProducts(productsEvent.min,productsEvent.max,productsEvent.category)
                getCategories()
            }
        }
    }
}