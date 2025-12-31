package com.example.trains.vm

sealed interface LinePart {
    val id: Int
}

data class LineSegment(
    override val id : Int,
    val start: Station,
    val end: Station,
) : LinePart

data class LineEnd(
    override val id: Int,
    val station: Station
) : LinePart