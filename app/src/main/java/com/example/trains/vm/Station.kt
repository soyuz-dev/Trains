package com.example.trains.vm

import androidx.compose.ui.geometry.Offset

data class Station(
    val id: Int, // unique identifier
    val shape: StationShape, // circle, square, triangle, etc.
    val position: Offset, // for rendering
)
