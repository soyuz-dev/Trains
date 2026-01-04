package com.example.trains.drawings


import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.sqrt

fun DrawScope.drawOutlinedCircle(
    center: Offset,
    radius: Float,
    fillColor: Color,
    strokeColor: Color,
    strokeWidth: Float
) {
    drawCircle(
        color = fillColor,
        radius = radius,
        center = center
    )

    drawCircle(
        color = strokeColor,
        radius = radius,
        center = center,
        style = Stroke(width = strokeWidth)
    )
}


fun DrawScope.drawOutlinedSquare(
    center:Offset,
    size: Float,
    fillColor: Color,
    strokeColor: Color,
    strokeWidth: Float
) {




    val topLeft = Offset(
        x = center.x - size,
        y = center.y - size
    )
    val twice = 2 * size

    drawRect(
        color = fillColor,
        topLeft = topLeft,
        size = Size(twice, twice)
    )

    drawRect(
        color = strokeColor,
        topLeft = topLeft,
        size = Size(twice, twice),
        style = Stroke(width = strokeWidth)
    )
}



fun DrawScope.drawOutlinedTriangle(
    center: Offset,
    size: Float,
    fillColor: Color,
    strokeColor: Color,
    strokeWidth: Float
) {
    val length = 2f * size

    val height = length * sqrt(3f)

    val correction = height/12

    val path = Path().apply {

        moveTo(
            center.x,
            center.y - height/4
        )


        lineTo(
            center.x - size,
            center.y + height/4
        )


        lineTo(
            center.x + size,
            center.y + height/4
        )

        close()
    }

    // Fill
    drawPath(
        path = path,
        color = fillColor
    )

    // Stroke
    drawPath(
        path = path,
        color = strokeColor,
        style = Stroke(width = strokeWidth)
    )
}



