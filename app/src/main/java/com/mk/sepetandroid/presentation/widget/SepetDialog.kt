package com.mk.sepetandroid.presentation.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.mk.sepetandroid.domain.model.view.DialogModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SepetBasicDialog(
    showDialog : Boolean,
    closeDialog : () -> Unit,
    message: String,
    icon : Int
) {

    val height = LocalConfiguration.current.screenHeightDp
    val width = LocalConfiguration.current.screenWidthDp

    if(showDialog) {
        BasicAlertDialog(
            onDismissRequest = {
                closeDialog()
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
            )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

                modifier = Modifier
                    .height((height * .7).dp)
                    .width((width * .9).dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                    .padding(
                        start = (width * .05).dp,
                        end = (width * .05).dp
                    )
            ){
                Icon(
                    painterResource(id = icon),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .height((height * .25).dp)
                        .width((width * .7).dp)
                )

                Spacer(modifier = Modifier.padding(10.dp))

                SepetText(
                    text = message,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.padding(10.dp))

                SepetButton(
                    text = "Ok".uppercase() ,
                    onClick = {
                        closeDialog()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((height * .09).dp)
                )

            }
        }
    }
}