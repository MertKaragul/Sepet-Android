package com.mk.sepetandroid.presentation.order.view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mk.sepetandroid.R
import com.mk.sepetandroid.common.Constants
import com.mk.sepetandroid.domain.model.enums.RouterEnum
import com.mk.sepetandroid.presentation.order.OrderEvent
import com.mk.sepetandroid.presentation.order.OrderState
import com.mk.sepetandroid.presentation.widget.SepetText
import com.mk.sepetandroid.presentation.widget.SepetTopBar
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderView(
    state : OrderState,
    onEvent : (OrderEvent) -> Unit,
    navigation  : (RouterEnum) -> Unit
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            SepetTopBar(
                title = { SepetText(text = "Orders") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigation(RouterEnum.ROUTE_BACK)
                        }
                    ) {
                        Icon(
                            painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    ){
        LazyColumn(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding()
                )
                .fillMaxSize()
        ) {
            items(state.orders){
                val date = ZonedDateTime.parse(it.createdDate, DateTimeFormatter.ISO_DATE_TIME)
                var clickable by remember { mutableStateOf(false) }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(20.dp)
                        .animateContentSize()
                        .clickable { clickable = !clickable }
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Column {
                            SepetText(
                                text = "${date.dayOfMonth}-${date.dayOfWeek}-${date.year} order",
                                color = MaterialTheme.colorScheme.onPrimary
                            )

                            SepetText(
                                text = "${it.products.size} products ordered",
                                color = MaterialTheme.colorScheme.onPrimary
                            )


                            if (!clickable)
                            SepetText(
                                "See for more info click",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onPrimary

                            )
                        }

                        Box(
                            modifier = Modifier
                                .wrapContentWidth()
                        ) {
                            if(it.products.first() == it.products.last())
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data("${Constants.URL}storage/${it.products.first().product.image.first()}")
                                        .build(),
                                    contentDescription = "Order image",
                                    modifier = Modifier
                                        .padding(start = 15.dp),
                                )
                            else {
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data("${Constants.URL}storage/${it.products.first().product.image.first()}")
                                        .build(),
                                    contentDescription = "Order image",
                                    modifier = Modifier
                                        .padding(start = 15.dp),
                                )

                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data("${Constants.URL}storage/${it.products.last().product.image.first()}")
                                        .build(),
                                    contentDescription = "Order image",
                                )
                            }

                        }
                    }

                    if (clickable){
                        it.products.forEach {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data("${Constants.URL}storage/${it.product.image.first()}")
                                        .build(),
                                    contentDescription = "Order image",
                                    contentScale = ContentScale.Fit,
                                    alignment = Alignment.CenterStart,
                                    modifier = Modifier
                                        .width(60.dp)
                                        .height(60.dp)
                                )

                                Column {
                                    SepetText(
                                        it.product.name ?: "",
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )

                                    SepetText(
                                        "x${it.count.toString() ?: 1.toString()} ",
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }
                        }


                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            SepetText(
                                text = "Total",
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            SepetText(
                                text = it.total.toString(),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }

                        Spacer(modifier = Modifier.padding(10.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ){
                            SepetText(
                                text = "Address",
                                color = MaterialTheme.colorScheme.onPrimary
                            )

                            SepetText(
                                text = it.address?.address ?: "No address found",
                                color = MaterialTheme.colorScheme.onPrimary,
                                textAlign = TextAlign.End
                            )
                        }
                    }


                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        onEvent(OrderEvent.GetOrders)
    }
}