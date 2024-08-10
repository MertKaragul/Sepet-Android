package com.mk.sepetandroid.presentation.profile.view

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.sepetandroid.domain.model.enums.RouterEnum
import com.mk.sepetandroid.domain.model.view.ProfileItemModel
import com.mk.sepetandroid.presentation.profile.ProfileEvent
import com.mk.sepetandroid.presentation.profile.ProfileState
import com.mk.sepetandroid.presentation.widget.NotFound
import com.mk.sepetandroid.presentation.widget.SepetButton
import com.mk.sepetandroid.presentation.widget.SepetLoading
import com.mk.sepetandroid.presentation.widget.SepetText
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay

@Composable
fun ProfileView(
    navHost : (RouterEnum) -> Unit,
    state : ProfileState,
    onEvent : (ProfileEvent) -> Unit
) {
    val height = LocalConfiguration.current.screenHeightDp
    val width = LocalConfiguration.current.screenWidthDp
    val profileActions = ProfileItemModel().getProfileItems()

    LaunchedEffect(key1 = state.routePage) {
        if (state.routePage != null)
            navHost(state.routePage)
    }


    if (state.isLoading){
        SepetLoading()
    }else if(state.isError){
        NotFound(text = state.message.toString()) {

        }
    }else{
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ){
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile image",
                    modifier = Modifier
                        .width((width * .15).dp)
                        .height((height * .1).dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(10.dp)
                        )
                )

                Spacer(
                    modifier = Modifier
                        .padding(7.dp)
                )

                Column{
                    SepetText(
                        text = state.profile?.username ?: "",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier
                            .padding(4.dp)
                    )

                    SepetText(
                        text = state.profile?.email ?: "",
                        fontSize = 15.sp,
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .padding(7.dp)
            )
            HorizontalDivider()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ){
                items(profileActions){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(
                                15.dp
                            )
                            .clickable {
                                it.route?.let(navHost)
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = it.icon!!),
                            contentDescription = "${it.name} icon",
                            modifier = Modifier
                                .width((width * .07).dp)
                                .height((height * .07).dp),

                            tint = MaterialTheme.colorScheme.primary
                        )

                        SepetText(
                            text = it.name!!,
                            fontSize = 20.sp,
                        )

                        Icon(
                            Icons.AutoMirrored.Default.KeyboardArrowRight,
                            contentDescription = "${it.name} route",
                            modifier = Modifier
                                .width((width * .07).dp)
                                .height((height * .07).dp),
                        )
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}