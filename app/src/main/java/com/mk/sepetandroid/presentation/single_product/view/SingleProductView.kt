package com.mk.sepetandroid.presentation.single_product.view

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.mk.sepetandroid.domain.model.response.CartModel
import com.mk.sepetandroid.presentation.single_product.SingleProductEvent
import com.mk.sepetandroid.presentation.single_product.SingleProductState
import com.mk.sepetandroid.presentation.widget.NotFound
import com.mk.sepetandroid.presentation.widget.SepetBasicDialog
import com.mk.sepetandroid.presentation.widget.SepetLoading
import com.mk.sepetandroid.presentation.widget.SepetText

@Composable
fun SingleProductView(
    onEvent : (SingleProductEvent) -> Unit,
    state : SingleProductState,
    id : String?,
) {
    val context = LocalContext.current
    val height = LocalConfiguration.current.screenHeightDp
    val width = LocalConfiguration.current.screenWidthDp


    var count by remember { mutableIntStateOf(1) }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if(state.isLoading){
            SepetLoading()
        }else if(state.isError) {
            NotFound(text = state.message ?: "Something went wrong") {
                onEvent(SingleProductEvent.GetProduct(id))
            }
        }else{
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((height * .2).dp)
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = 40.dp,
                                    bottomEnd = 40.dp
                                )
                            )
                            .background(MaterialTheme.colorScheme.primary),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data("${Constants.URL}storage/${state.productModel?.image?.first()}")
                                .build(),
                            contentDescription = "${state.productModel?.name} image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .width((width * .15).dp)
                        )
                    }

                    Column(
                        modifier = Modifier.padding(10.dp)
                    ){
                        Spacer(modifier = Modifier.padding(5.dp))

                        SepetText(
                            text = state.productModel?.name.toString(),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Bold
                        )

                        SepetText(
                            text = state.productModel?.description.toString(),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 15.sp
                        )

                        Spacer(modifier = Modifier.padding(5.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.padding(5.dp))

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
                                    },
                                    modifier = Modifier
                                        .height(50.dp)
                                        .width(50.dp),
                                    contentPadding = PaddingValues(0.dp),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.baseline_remove_24),
                                        contentDescription = "Decrease product count",
                                    )
                                }
                                Spacer(modifier = Modifier.padding(5.dp))

                                Column(
                                    modifier = Modifier
                                        .height(50.dp)
                                        .width(50.dp)
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
                                    },
                                    modifier = Modifier
                                        .height(50.dp)
                                        .width(50.dp),
                                    contentPadding = PaddingValues(0.dp),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.baseline_add_24),
                                        contentDescription = "Increase product count",
                                    )
                                }
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                SepetText(
                                    text = "${state.productModel?.price.toString()}$",
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontSize = 30.sp,
                                    textDecoration = if (state.productModel?.discountStatus == true) TextDecoration.LineThrough else TextDecoration.None
                                )

                                if (state.productModel?.discountStatus == true)
                                    SepetText(
                                        text = "${state.productModel
                                            .discountPrice.toString()}$",
                                        color = MaterialTheme.colorScheme.secondary,
                                        fontSize = 25.sp
                                    )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Button(
                    onClick = {
                        onEvent(
                            SingleProductEvent.AddProduct(
                                CartActionModel(
                                    id ?: "",
                                    count,
                                    ""
                                )
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((height * .1).dp)
                        .padding(10.dp),

                    shape = RoundedCornerShape(
                        10.dp
                    ),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    )

                ) {
                    SepetText(
                        text = "Add to cart",
                        fontSize = 15.sp,
                    )
                }
            }
        }
    }

    SepetBasicDialog(
        showDialog = state.isSuccess,
        closeDialog = { onEvent(SingleProductEvent.CloseDialog) },
        message = state.message ?: "Something went wrong",
        icon = R.drawable.baseline_check_circle_24
    )

    LaunchedEffect(key1 = Unit) {
        onEvent(SingleProductEvent.GetProduct(id))
    }

}