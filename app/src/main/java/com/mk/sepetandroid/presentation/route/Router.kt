package com.mk.sepetandroid.presentation.route

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mk.sepetandroid.domain.model.enums.RouterEnum
import com.mk.sepetandroid.domain.model.view.NavBarItem
import com.mk.sepetandroid.presentation.address.AddressViewModel
import com.mk.sepetandroid.presentation.address.view.AddressView
import com.mk.sepetandroid.presentation.cart.CartViewModel
import com.mk.sepetandroid.presentation.cart.view.CartView
import com.mk.sepetandroid.presentation.mydetails.MyDetailsViewModel
import com.mk.sepetandroid.presentation.mydetails.view.MyDetailsView
import com.mk.sepetandroid.presentation.order.OrderViewModel
import com.mk.sepetandroid.presentation.order.view.OrderView
import com.mk.sepetandroid.presentation.products.ProductsViewModel
import com.mk.sepetandroid.presentation.products.view.ProductsView
import com.mk.sepetandroid.presentation.profile.ProfileViewModel
import com.mk.sepetandroid.presentation.profile.view.ProfileView
import com.mk.sepetandroid.presentation.signin.SignInViewModel
import com.mk.sepetandroid.presentation.signin.view.SignInView
import com.mk.sepetandroid.presentation.signup.SignUpViewModel
import com.mk.sepetandroid.presentation.signup.view.SignUpView
import com.mk.sepetandroid.presentation.single_product.SingleProductViewModel
import com.mk.sepetandroid.presentation.single_product.view.SingleProductView
import com.mk.sepetandroid.presentation.widget.SepetText

@Composable
fun Router() {
    val navHostController = rememberNavController()
    val getNavItems = NavBarItem().getNavBarItems()
    var clickedItem by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            BottomAppBar(
                actions = {
                    getNavItems.forEachIndexed { index, navBarItem ->
                        NavigationBarItem(
                            selected = clickedItem == index,
                            onClick = {
                                navHostController.navigate(navBarItem.route.name)
                                clickedItem = index
                            },

                            icon = {
                                Icon(
                                    navBarItem.icon,
                                    contentDescription = "${navBarItem.label} page"
                                )
                            },
                            label = {
                                SepetText(
                                    text = navBarItem.label,
                                )
                            },

                        )
                    }
                }
            )
        },

        content = { it ->

            NavHost(
                navController = navHostController,
                startDestination = RouterEnum.PRODUCTS_SCREEN.name,
                modifier = Modifier
                    .padding(
                        top = it.calculateTopPadding(),
                        bottom = it.calculateBottomPadding()
                    )
            ){
                composable(
                    RouterEnum.SIGN_IN.name
                ){
                    val viewModel = hiltViewModel<SignInViewModel>()
                    val state = viewModel.state.value
                    SignInView(
                        navHost = {
                            navHostController.navigate(it.name)
                        },
                        state,
                        viewModel::onEvent
                    )
                }

                composable(
                    RouterEnum.PROFILE_SCREEN.name
                ){
                    val viewModel = hiltViewModel<ProfileViewModel>()
                    val state = viewModel.state.value

                    ProfileView(
                        navHost = {
                            navHostController.navigate(it.name)
                        },
                        state,
                        viewModel::onEvent
                    )
                }

                composable(
                    RouterEnum.CART_SCREEN.name
                ){
                    val viewmodel = hiltViewModel<CartViewModel>()
                    val state = viewmodel.state.value
                    CartView(
                        viewmodel::onEvent,
                        state
                    )
                }

                composable(
                    RouterEnum.PRODUCTS_SCREEN.name
                ){
                    val viewModel = hiltViewModel<ProductsViewModel>()
                    val state = viewModel.state.value
                    ProductsView(
                        viewModel::onEvent,
                        route = { routerEnum, id ->
                            navHostController.navigate("${routerEnum.name}/${id}")
                        },
                        state,

                    )
                }

                composable(
                    "${RouterEnum.SINGLE_PRODUCT_SCREEN.name}/{productId}",
                    arguments = listOf(navArgument("productId"){ type = NavType.StringType })
                ){
                    val viewModel = hiltViewModel<SingleProductViewModel>()
                    val state = viewModel.state.value
                    val id = it.arguments?.getString("productId")
                    SingleProductView(
                        viewModel::onEvent,
                        state,
                        id
                    )
                }

                composable(RouterEnum.SIGN_UP.name){
                    val viewModel = hiltViewModel<SignUpViewModel>()
                    val state = viewModel.state.value
                    SignUpView(
                        navHostController = {
                            navHostController.navigate(it.name)
                        },
                        state,
                        viewModel::onEvent
                    )
                }

                composable(RouterEnum.ADDRESS_SCREEN.name){
                    val viewModel = hiltViewModel<AddressViewModel>()
                    val state = viewModel.state.value
                    AddressView(
                        navigate = {
                            if ((it == RouterEnum.ROUTE_BACK))
                                navHostController.navigateUp()
                            else
                                navHostController.navigate(it.name)
                        },
                        state,
                        onEvent = viewModel::onEvent
                    )
                }

                composable(RouterEnum.ORDER_VIEW.name){
                    val viewModel = hiltViewModel<OrderViewModel>()
                    val state = viewModel.state.value
                    OrderView(
                        state,
                        viewModel::onEvent,
                        navigation = {
                            if (it == RouterEnum.ROUTE_BACK)
                                navHostController.navigateUp()
                        }
                    )
                }

                composable(RouterEnum.MY_DETAILS.name){
                    val viewModel = hiltViewModel<MyDetailsViewModel>()
                    val state = viewModel.state.value
                    MyDetailsView(
                        onNavigate = {
                            if (it == RouterEnum.ROUTE_BACK)
                                navHostController.navigateUp()
                        },
                        state,
                        viewModel::onEvent
                    )
                }
            }
        }
    )
}