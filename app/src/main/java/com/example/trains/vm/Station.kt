package com.example.trains.vm

import androidx.compose.ui.geometry.Offset
import com.example.trains.ux.StationShape

data class Station(
    val id: Int, // unique identifier
    val shape: StationShape, // circle, square, triangle, etc.
    val position: Offset, // for rendering
)
