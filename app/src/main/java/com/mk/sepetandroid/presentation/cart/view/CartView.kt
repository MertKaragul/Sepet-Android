package com.mk.sepetandroid.presentation.cart.view

import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mk.sepetandroid.R
import com.mk.sepetandroid.common.Constants
import com.mk.sepetandroid.domain.model.response.CartActionModel
import com.mk.sepetandroid.presentation.cart.CartEvent
import com.mk.sepetandroid.presentation.cart.CartState
import com.mk.sepetandroid.presentation.widget.NotFound
import com.mk.sepetandroid.presentation.widget.SepetButton
import com.mk.sepetandroid.presentation.widget.SepetLoading
import com.mk.sepetandroid.presentation.widget.SepetText
import com.mk.sepetandroid.presentation.widget.SepetTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartView(
    onEvent : (CartEvent) -> Unit,
    state : CartState
) {
    val width = LocalConfiguration.current.screenWidthDp
    val height = LocalConfiguration.current.screenHeightDp
    var takeOrder by remember {
        mutableStateOf(false)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val setProductBlur = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        Modifier
            .blur(if (takeOrder) 4.dp else 0.dp)
    }else {
        Modifier
            .alpha(if (takeOrder) 0.5F else 1f)
    }

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),

        topBar = {
            SepetTopBar(
                title = {
                    SepetText(
                        text = "Cart"
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding()
                )
        ) {
            if(state.isLoading){
                SepetLoading()
            }else if(state.isError){
                NotFound(text = state.message ?: "Something went wrong") {
                    onEvent(CartEvent.GetCart)
                }
            }else{
                if(state.isSuccess){
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        SepetText(
                            text = state.message ?: "",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }else if(state.cart.isEmpty()){
                    NotFound(text = "Your cart is empty") {}
                }else{
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        LazyColumn(
                            modifier = setProductBlur
                                .wrapContentHeight()
                        ){
                            items(state.cart.first().products){
                                var count by remember {
                                    mutableIntStateOf(it.count)
                                }
                                Column(
                                    modifier = Modifier
                                        .padding(10.dp)
                                ){
                                    Row {
                                        AsyncImage(
                                            model = ImageRequest.Builder(context)
                                                .data("${Constants.URL}storage/${it.product.image.first()}")
                                                .build(),
                                            contentDescription = "${it.product.name} image",
                                            contentScale = ContentScale.Fit,
                                            modifier = Modifier
                                                .width((width * .25).dp)
                                                .height((height * .15).dp)
                                        )

                                        Column {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ){
                                                SepetText(
                                                    text = it.product.name ?: "",
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 20.sp
                                                )

                                                Icon(
                                                    Icons.Default.Clear,
                                                    contentDescription = "",
                                                    modifier = Modifier
                                                        .clickable {
                                                            onEvent(
                                                                CartEvent.DeleteProduct(
                                                                    it.product.id ?: ""
                                                                )
                                                            )
                                                        }
                                                )
                                            }

                                            SepetText(
                                                text = it.product.description ?: ""
                                            )

                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ){
                                                Row{

                                                    Button(
                                                        onClick = {
                                                            if (count != 1)
                                                                count--

                                                            onEvent(
                                                                CartEvent.UpdateCart(
                                                                    CartActionModel(
                                                                        it.product.id ?: "",
                                                                        count
                                                                    )
                                                                )
                                                            )
                                                        },
                                                        modifier = Modifier
                                                            .height(30.dp)
                                                            .width(30.dp),
                                                        contentPadding = PaddingValues(0.dp),
                                                        shape = RoundedCornerShape(10.dp)
                                                    ) {
                                                        Icon(
                                                            painter = painterResource(id = R.drawable.baseline_remove_24),
                                                            contentDescription = "Decrease product count",
                                                        )
                                                    }
                                                    Spacer(modifier = Modifier.padding(5.dp))

                                                    Column(
                                                        modifier = Modifier
                                                            .height(30.dp)
                                                            .width(30.dp)
                                                            .border(
                                                                1.dp,
                                                                MaterialTheme.colorScheme.primary,
                                                                RoundedCornerShape(10.dp)
                                                            ),
                                                        verticalArrangement = Arrangement.Center,
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
                                                        SepetText(
                                                            text = count.toString(),
                                                            textAlign = TextAlign.Center,
                                                            fontSize = 20.sp
                                                        )
                                                    }

                                                    Spacer(modifier = Modifier.padding(5.dp))
                                                    Button(
                                                        onClick = {
                                                            count++
                                                            onEvent(
                                                                CartEvent.UpdateCart(
                                                                    CartActionModel(
                                                                        productId = it.product.id ?: "",
                                                                        count = count,
                                                                    )
                                                                )
                                                            )
                                                        },
                                                        modifier = Modifier
                                                            .height(30.dp)
                                                            .width(30.dp),
                                                        contentPadding = PaddingValues(0.dp),
                                                        shape = RoundedCornerShape(10.dp)
                                                    ) {
                                                        Icon(
                                                            painter = painterResource(id = R.drawable.baseline_add_24),
                                                            contentDescription = "Increase product count",
                                                        )
                                                    }
                                                }

                                                Column(
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ){
                                                    SepetText(
                                                        text = "${it.product.price}$",
                                                        fontSize = 20.sp,
                                                        textDecoration = if (it.product.discountStatus) TextDecoration.LineThrough else TextDecoration.None
                                                    )

                                                    if (it.product.discountStatus)
                                                        SepetText(
                                                            text = "${it.product.discountPrice}$",
                                                            fontSize = 18.sp
                                                        )
                                                }
                                            }
                                        }
                                    }
                                    HorizontalDivider()
                                }
                            }

                            items(1){
                                Button(
                                    onClick = {
                                        takeOrder = !takeOrder
                                    },
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxWidth()
                                        .height((height * .08).dp)
                                        .padding(start = 10.dp, end = 10.dp),
                                    shape = RoundedCornerShape(10.dp),
                                    enabled = !takeOrder,
                                ) {
                                    SepetText(
                                        text = "Take order"
                                    )
                                }
                            }
                        }


                    }
                }
            }
        }
    }

    AnimatedVisibility(
        visible = takeOrder,
        enter = slideInVertically{ fullHeight: Int ->
            +fullHeight
        },
        exit = slideOutVertically { fullHeight: Int ->
            +fullHeight
        }

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = interactionSource,
                ) {
                    // If clicked show user's other address
                    takeOrder = !takeOrder
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((height * .5).dp)
                    .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(20.dp)
                    .clickable(indication = null, interactionSource = interactionSource) { }
            ) {
                SepetText(
                    text = "Order",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondary
                )


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((height * .09).dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    SepetText(
                        text = "Delivery",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        SepetText(
                            text = if(state.cart.isNotEmpty()) state.cart.first().address?.name ?: "No address" else "No address",
                            color = MaterialTheme.colorScheme.onSecondary
                        )

                        SepetText(
                            text = if(state.cart.isNotEmpty()) state.cart.first().address?.address ?: "Add or select new address" else "Add or select new address",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }


                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    items(state.address){
                        Column(
                            modifier = Modifier
                                .padding(5.dp)
                                .clickable {
                                    onEvent(
                                        CartEvent.UpdateCart(
                                            CartActionModel(
                                                address = it.id ?: ""
                                            )
                                        )
                                    )
                                }
                                .wrapContentWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.secondaryContainer)
                                .padding(5.dp)

                        ){
                            SepetText(
                                text = it.name ?: "",
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )

                            SepetText(
                                text = it.address ?: "",
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((height * .09).dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    SepetText(
                        text = "Payment",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )

                    SepetText(
                        text = "Cart",
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((height * .09).dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    SepetText(
                        text = "Total",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )

                    SepetText(
                        text = "${ if(state.cart.isNotEmpty()) state.cart.first().total else "0.0"}$",
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }



                SepetButton(
                    text = "Order" ,
                    onClick = {
                        takeOrder = !takeOrder
                        onEvent(
                            CartEvent.PlaceOrder
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((height * .09).dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    )
                )
            }
        }
    }


    LaunchedEffect(key1 = Unit){
        onEvent(CartEvent.GetCart)
    }
}