package com.example.trains.vm

class Line(

    val id : Int,
    val stations : List<Station>,
    val lineParts : List<LinePart>,
    val previewStations :List<Station> = stations,
    val previewLineParts :List<LinePart> = lineParts,

)