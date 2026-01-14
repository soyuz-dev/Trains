package com.example.trains.vm

import androidx.compose.ui.geometry.Offset
import com.example.trains.ux.StationShape

data class Station (
    val id: Int,
    val shape: StationShape,
    val position: Offset,
)
