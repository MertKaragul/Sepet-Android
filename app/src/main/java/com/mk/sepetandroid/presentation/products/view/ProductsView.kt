package com.mk.sepetandroid.presentation.products.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mk.sepetandroid.common.Constants
import com.mk.sepetandroid.domain.model.enums.RouterEnum
import com.mk.sepetandroid.presentation.products.ProductsEvent
import com.mk.sepetandroid.presentation.products.ProductsState
import com.mk.sepetandroid.presentation.widget.NotFound
import com.mk.sepetandroid.presentation.widget.SepetButton
import com.mk.sepetandroid.presentation.widget.SepetLoading
import com.mk.sepetandroid.presentation.widget.SepetText
import kotlinx.coroutines.launch

@Composable
fun ProductsView(
    onEvent : (ProductsEvent) -> Unit,
    route : (RouterEnum, id : String) -> Unit,
    state: ProductsState,
) {
    val height = LocalConfiguration.current.screenHeightDp
    val width = LocalConfiguration.current.screenWidthDp
    val context = LocalContext.current
    val listState = rememberLazyStaggeredGridState()


    if(state.isLoading){
        SepetLoading()
    }else if(state.isError){
        NotFound(text = state.message ?: "Something went wrong") {}
    }else{
        if(state.products.isEmpty()){
            NotFound(text = "No products found") {}
        }else{
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                ){
                    items(state.categories.reversed()){
                        SepetButton(
                            modifier = Modifier
                                .padding(10.dp),
                            text = it.name,
                            onClick = {
                                // Get products
                                onEvent(ProductsEvent.GetProducts(state.min,state.max,it.id))
                            }
                        )
                    }
                }


                LazyVerticalStaggeredGrid(
                    userScrollEnabled = true,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = (height * .01).dp,
                            start = 10.dp,
                            end = 10.dp
                        ),
                    columns = StaggeredGridCells.Fixed(2),
                    verticalItemSpacing = 4.dp,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    state = listState
                ) {
                    items(state.products){
                        Column(
                            modifier = Modifier
                                .width((width * .3).dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.primary)
                                .padding(20.dp)
                                .clickable {
                                    route(RouterEnum.SINGLE_PRODUCT_SCREEN, it.id ?: "")
                                },
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data("${Constants.URL}storage/${it.image.first()}" )
                                    .build(),
                                contentDescription = "${it.name} category",
                                modifier = Modifier
                                    .height((height * .09).dp),
                                contentScale = ContentScale.Fit
                            )

                            SepetText(
                                text = it.name.toString(),
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )


                            SepetText(
                                text = it.description.toString(),
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Normal
                            )

                            Row(){
                                SepetText(
                                    text = it.price.toString() + "$",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Normal,
                                    textDecoration = if (it.discountStatus) TextDecoration.LineThrough else TextDecoration.None
                                )

                                if (it.discountStatus)
                                    SepetText(
                                        text = it.discountPrice.toString() + "$",
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Normal
                                    )
                            }
                        }
                    }
                }


            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        onEvent(ProductsEvent.GetProducts(state.min,state.max,null))
    }
}
