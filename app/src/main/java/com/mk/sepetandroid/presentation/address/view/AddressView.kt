package com.mk.sepetandroid.presentation.address.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mk.sepetandroid.R
import com.mk.sepetandroid.domain.model.enums.RouterEnum
import com.mk.sepetandroid.domain.model.response.AddressModel
import com.mk.sepetandroid.presentation.address.AddressEvent
import com.mk.sepetandroid.presentation.address.AddressState
import com.mk.sepetandroid.presentation.widget.NotFound
import com.mk.sepetandroid.presentation.widget.SepetBasicDialog
import com.mk.sepetandroid.presentation.widget.SepetButton
import com.mk.sepetandroid.presentation.widget.SepetFieldWithLabel
import com.mk.sepetandroid.presentation.widget.SepetText
import com.mk.sepetandroid.presentation.widget.SepetTextField
import com.mk.sepetandroid.presentation.widget.SepetTopBar
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddressView(
    navigate : (RouterEnum) -> Unit,
    state : AddressState,
    onEvent : (AddressEvent) -> Unit
) {
    var userWantEditAddress by remember { mutableStateOf<AddressModel?>(null) }

    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf( "") }
    var apartment by remember { mutableStateOf("") }
    var postCode by remember { mutableStateOf("") }

    LaunchedEffect(key1 = userWantEditAddress != null) {
        name = userWantEditAddress?.name ?: ""
        address = userWantEditAddress?.address ?: ""
        apartment = userWantEditAddress?.apartment ?: ""
        postCode = userWantEditAddress?.postCode ?: ""
    }


    val height = LocalConfiguration.current.screenHeightDp
    val width = LocalConfiguration.current.screenWidthDp

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = {2})

    LaunchedEffect(key1 = Unit) {
        onEvent(AddressEvent.GetAddress)
    }


    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {

            SepetTopBar(
                title = {
                    SepetText(
                        text = if (pagerState.currentPage == 0)
                            "Delivery address"
                        else if(userWantEditAddress != null)
                            "Edit address"
                        else
                            "Add new address"
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if(userWantEditAddress != null){
                                userWantEditAddress = null
                                scope.launch {
                                    pagerState.animateScrollToPage(0)
                                }
                            }
                            else if(pagerState.currentPage == 1)
                                scope.launch {
                                    pagerState.animateScrollToPage(0)
                                }
                            else
                                navigate(RouterEnum.ROUTE_BACK)
                        },
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                                contentDescription = "",
                            )
                        }
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                nextPage(pagerState)
                            }
                        },
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_save_24),
                                contentDescription = "",
                            )
                        },
                    )
                }
            )
        },
        content = { it ->

            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false
            ) { page ->
                when(page){
                    0 -> {
                        when(state.addressList.isEmpty()){
                            true -> {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    SepetText(
                                        text = "No address found",
                                        fontSize = 25.sp
                                    )
                                }
                            }

                            false -> {
                                LazyColumn(
                                    modifier = Modifier
                                        .padding(top = it.calculateTopPadding())
                                        .fillMaxSize()
                                ) {
                                    items(state.addressList){
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(10.dp)
                                                .clip(RoundedCornerShape(10.dp))
                                                .background(
                                                    MaterialTheme.colorScheme.primary
                                                ),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            Column(
                                                modifier = Modifier
                                                    .padding(10.dp)
                                                    .clip(RoundedCornerShape(30.dp))
                                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                                    .size(50.dp),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center
                                            ){
                                                Icon(
                                                    Icons.Default.LocationOn,
                                                    contentDescription = "",
                                                )
                                            }

                                            Column(
                                                modifier = Modifier
                                                    .padding(5.dp)
                                                    .fillMaxWidth(),
                                                horizontalAlignment = Alignment.Start
                                            ){
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ){
                                                    SepetText(
                                                        it.name.toString(),
                                                        color = MaterialTheme.colorScheme.onPrimary
                                                    )

                                                    Row {
                                                        Icon(
                                                            Icons.Default.Edit,
                                                            contentDescription = "",
                                                            tint = MaterialTheme.colorScheme.onPrimary,
                                                            modifier = Modifier
                                                                .padding(end = 10.dp)
                                                                .clickable {
                                                                    userWantEditAddress = it
                                                                    scope.launch {
                                                                        nextPage(pagerState)
                                                                    }
                                                                }

                                                        )

                                                        Icon(
                                                            Icons.Default.Delete,
                                                            contentDescription = "",
                                                            tint = MaterialTheme.colorScheme.onPrimary,
                                                            modifier = Modifier
                                                                .clickable {
                                                                    onEvent(
                                                                        AddressEvent.DeleteAddress(
                                                                            it
                                                                        )
                                                                    )
                                                                }
                                                        )
                                                    }
                                                }

                                                SepetText(
                                                    text = it.address.toString(),
                                                    color = MaterialTheme.colorScheme.onPrimary
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    1 -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(
                                    top = it.calculateTopPadding(),
                                    start = 10.dp,
                                    end = 10.dp
                                ),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            SepetFieldWithLabel(
                                label = "Address" ,
                                text = address,
                                textChanged = {address = it},
                                modifier = Modifier
                                    .fillMaxWidth(),
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.LocationOn,
                                        contentDescription = ""
                                    )
                                }
                            )

                            Spacer(modifier = Modifier.padding(5.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                SepetFieldWithLabel(
                                    label = "Post Code",
                                    text = postCode,
                                    textChanged = {postCode = it.filter {text -> text.isDigit() }.toString()},
                                    modifier = Modifier
                                        .width((width * .45).dp)
                                )

                                Spacer(modifier = Modifier.padding(10.dp))

                                SepetFieldWithLabel(
                                    label = "Apartment",
                                    text = apartment,
                                    textChanged = {
                                        apartment = it.filter {text -> text.isDigit() }.toString()
                                    },
                                    modifier = Modifier,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                )
                            }

                            Spacer(modifier = Modifier.padding(5.dp))

                            SepetFieldWithLabel(
                                label = "Address title",
                                text = name,
                                textChanged = {
                                    name = it
                                },
                                modifier = Modifier
                                    .fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            Spacer(modifier = Modifier.padding(10.dp))
                            SepetButton(
                                text = "Save",
                                onClick = {
                                    if (userWantEditAddress != null){
                                        onEvent(
                                            AddressEvent.UpdateAddress(
                                                AddressModel(
                                                    userWantEditAddress?.id,
                                                    name,
                                                    address,
                                                    postCode,
                                                    apartment
                                                )
                                            )
                                        )
                                    }else{
                                        onEvent(
                                            AddressEvent.AddAddress(
                                                AddressModel(
                                                    "",
                                                    name,
                                                    address,
                                                    postCode,
                                                    apartment
                                                )
                                            )
                                        )
                                    }

                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height((60).dp)
                            )
                        }
                    }
                }
            }

        }
    )
    SepetBasicDialog(
        showDialog = state.isError || state.isSuccess,
        closeDialog = { onEvent(AddressEvent.CloseDialog) },
        message = state.message ?: "Something went wrong" ,
        icon = if (state.isError) R.drawable.baseline_error_24 else R.drawable.baseline_check_circle_24
    )

}

@OptIn(ExperimentalFoundationApi::class)
private suspend fun nextPage(pagerState : PagerState) {
    pagerState.animateScrollToPage(
        if (pagerState.currentPage == (pagerState.pageCount - 1)) 0 else pagerState.currentPage + 1,
        animationSpec = spring(
            stiffness = Spring.StiffnessLow
        )
    )
}

@Preview
@Composable
private fun AddressPreview() {
    MaterialTheme {
        val state = AddressState(
            addressList = listOf(
                AddressModel(
                    "0",
                    "Home",
                    "Turkey, Istanbul, Kadıköy"
                ),
                AddressModel(
                    "1",
                    "Work",
                    "Turkey, Istanbul, Kadıköy"
                ),
                AddressModel(
                    "2",
                    "Work 2",
                    "Turkey, Istanbul, Kadıköy"
                )
            )
        )
        AddressView(navigate = {}, state = state) {

        }
    }
}