package com.mk.sepetandroid.presentation.mydetails.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mk.sepetandroid.R
import com.mk.sepetandroid.domain.model.enums.RouterEnum
import com.mk.sepetandroid.domain.model.user.ProfileModel
import com.mk.sepetandroid.presentation.mydetails.MyDetailsEvent
import com.mk.sepetandroid.presentation.mydetails.MyDetailsState
import com.mk.sepetandroid.presentation.widget.SepetBasicDialog
import com.mk.sepetandroid.presentation.widget.SepetButton
import com.mk.sepetandroid.presentation.widget.SepetFieldWithLabel
import com.mk.sepetandroid.presentation.widget.SepetText
import com.mk.sepetandroid.presentation.widget.SepetTopBar
import kotlinx.coroutines.delay

@Composable
fun MyDetailsView(
    onNavigate : (RouterEnum) -> Unit,
    state : MyDetailsState,
    onEvent : (MyDetailsEvent) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Sizes
    val height = LocalConfiguration.current.screenHeightDp
    val width = LocalConfiguration.current.screenWidthDp

    val pageWidgetsModifier = Modifier
        .fillMaxWidth()
        .height((height * .07).dp)

    Scaffold(
        topBar = {
            SepetTopBar(
                title = { SepetText(text = "My details") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onNavigate(RouterEnum.ROUTE_BACK)
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

    ) { it ->
        Column(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding() + 10.dp,
                    start = 10.dp,
                    end = 10.dp
                )
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SepetFieldWithLabel(
                label = "Name" ,
                text = name,
                textChanged = {name = it},
                modifier = pageWidgetsModifier,
            )

            Spacer(modifier = Modifier.padding(10.dp))

            SepetFieldWithLabel(
                label = "Email" ,
                text = email,
                textChanged = {email = it},
                modifier = pageWidgetsModifier,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )

            Spacer(modifier = Modifier.padding(10.dp))


            SepetFieldWithLabel(
                label = "Phone" ,
                text = phone,
                textChanged = {
                    Regex("[^0-9]").replace(it, "").let {
                        if(it.length < 11)
                            phone = it
                    }
                },
                modifier = pageWidgetsModifier,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                )
            )

            Spacer(modifier = Modifier.padding(10.dp))

            SepetButton(
                text = "Save",
                onClick = {
                    // Update profile
                    onEvent(
                        MyDetailsEvent.UpdateProfile(
                            ProfileModel(
                                username = name,
                                email = email,
                                password = password,
                                phone = phone
                            )
                        )
                    )
                },
                modifier = pageWidgetsModifier
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        onEvent(MyDetailsEvent.GetProfile)
    }

    SepetBasicDialog(
        showDialog = state.isSuccess || state.isError,
        closeDialog = {
            onEvent(MyDetailsEvent.CloseDialog)
        },
        message = state.message,
        icon = if (state.isSuccess) R.drawable.baseline_check_circle_24 else R.drawable.baseline_error_24
    )



    LaunchedEffect(key1 = state.profileModel != null) {
        if (state.profileModel != null){
            name = state.profileModel.username ?: ""
            email = state.profileModel.email ?: ""
            password = state.profileModel.password ?: ""
            phone = state.profileModel.phone ?: ""
        }
    }
}