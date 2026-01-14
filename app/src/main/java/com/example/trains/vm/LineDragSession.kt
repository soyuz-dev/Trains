package com.example.trains.vm

import androidx.compose.ui.geometry.Offset

sealed interface LineDragSession : GameAction {
    val lineId : Int
}


data class LineEndDragSession(
    override val lineId: Int,
    val end: LineEnd,
    var previewStations: List<Station>,
    var previewLineParts: List<LinePart>,
    var fingerLocation : Offset
) : LineDragSession {
    override fun releaseFinger() {

    }

    override fun commit() {

    }

    fun updateFingerLocation(fingerLocation: Offset?) {
        if (fingerLocation != null) {
            this.fingerLocation = fingerLocation
        } else {
            releaseFinger()
        }
    }
}

data class SegmentDragSession(
    override val lineId: Int,
    val segment: LineSeg,
    var previewStations: List<Station>,
    var previewLineParts: List<LinePart>,
    var fingerLocation : Offset
) : LineDragSession {

    override fun releaseFinger() {

    }

    override fun commit() {

    }

}