package com.example.debugger.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Layout{
    val bodyMargin: Dp
        @Composable get() = when(LocalConfiguration.current.screenWidthDp){
            in 0..599 -> 16.dp
            in 600..904 -> 32.dp
            in 905..1239 -> 100.dp
            in 1240..1439 -> 200.dp
            else -> 0.dp
        }
}