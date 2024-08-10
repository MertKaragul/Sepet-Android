package com.mk.sepetandroid.presentation.signup.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.sepetandroid.domain.model.enums.RouterEnum
import com.mk.sepetandroid.presentation.signup.SignUpEvent
import com.mk.sepetandroid.presentation.signup.SignUpState
import com.mk.sepetandroid.presentation.widget.SepetBasicDialog
import com.mk.sepetandroid.presentation.widget.SepetText

@Composable
fun SignUpView(
    navHostController: (RouterEnum) -> Unit,
    state : SignUpState,
    onEvent : (SignUpEvent) -> Unit,
) {
    val height = LocalConfiguration.current.screenHeightDp
    val width = LocalConfiguration.current.screenWidthDp

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){

        Column(
            modifier = Modifier
                .height((height * .4).dp)
                .width((width * .2).dp)
                .rotate(30F)
                .offset(
                    x = (-90).dp,
                    y = 0.dp
                )
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.secondary,
                    RoundedCornerShape(
                        topEnd = 100.dp,
                        bottomEnd = 100.dp
                    )
                )
        ) {}

        Column(
            modifier = Modifier
                .align(
                    Alignment.TopEnd
                )
                .height((height * .4).dp)
                .width((width * .7).dp)
                .rotate(-10F)
                .offset(
                    x = (0).dp,
                    y = (height - 200).dp
                )

                .border(
                    1.dp,
                    MaterialTheme.colorScheme.primary,
                    CircleShape
                )
        ) {}


        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SepetText(
                text = "Sign up",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )

            SepetText(
                text = "Let's sign up and start",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )


            SignUpForm(
                signUpClicked = {

                },
                navHost = {

                }
            )
        }

    }






//    SepetBasicDialog(
//        showDialog = state.isError,
//        closeDialog = {
//            onEvent(SignUpEvent.CloseDialog)
//        },
//        dialogModel = state.dialogModel,
//    )
}