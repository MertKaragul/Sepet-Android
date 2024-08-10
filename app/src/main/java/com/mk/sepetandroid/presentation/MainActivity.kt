package com.mk.sepetandroid.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mk.sepetandroid.presentation.route.Router
import com.mk.sepetandroid.presentation.ui.theme.SepetAndroidTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SepetAndroidTheme{
                Router()
            }
        }
    }
}