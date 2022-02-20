package com.example.debugger.data

import androidx.compose.ui.graphics.painter.Painter

data class Analysis(
    val title: String,
    val type: String,
    val graph: String,
    val image: Painter? = null
)
