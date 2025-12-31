package com.example.trains.vm

sealed interface LineDragSession : GameAction {
    val lineId : Int
}


data class LineEndDragSession(
    override val lineId: Int,
    val end: LineEnd,
    var previewStations: List<Station>,
    var previewLineParts: List<LinePart>
) : LineDragSession

data class SegmentDragSession(
    override val lineId: Int,
    val segment: LineSegment,
    var previewStations: List<Station>,
    var previewLineParts: List<LinePart>
) : LineDragSession