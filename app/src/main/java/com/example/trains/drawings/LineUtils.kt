package com.example.trains.drawings

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.trains.Vector2D
import com.example.trains.toOffset
import com.example.trains.toVector2D
import kotlin.math.*

private const val DEG45 = Math.PI / 4.0

fun snapDirections(angle: Double): Pair<Double, Double> {
    val k = angle / DEG45
    val low = floor(k) * DEG45
    val high = ceil(k) * DEG45
    return low to high
}

fun intersectRays(
    A: Vector2D, u: Vector2D,
    B: Vector2D, v: Vector2D
): Vector2D? {
    val w = B - A
    val det = u.cross(v)

    if (abs(det) < Vector2D.Companion.EPSILON) {
        return null // parallel or nearly parallel
    }

    val t = w.cross(v) / det
    return A + u * t
}


fun computePath(A: Vector2D, B: Vector2D): Pair<List<Vector2D>, Double> {
    val d = B - A
    val angle = atan2(d.y, d.x)

    // Straight-line snap
    val snapped = round(angle / (Math.PI / 4)) * (Math.PI / 4)
    if (abs(angle - snapped) < 3e-2) {
        return Pair(listOf(A, B), snapped)
    }

    val (a1, a2) = snapDirections(angle)
    val thetaA = a1

    val (b1, b2) = snapDirections(angle + PI)

    val thetaB = b2

    val u = Vector2D(cos(thetaA), sin(thetaA))
    val v = Vector2D(cos(thetaB), sin(thetaB))

    val C: Vector2D = intersectRays(A, u, B, v)
        ?: return Pair(listOf(A, B), thetaB - Math.PI)

    return Pair(listOf(A, C, B), thetaB - Math.PI)
}



fun DrawScope.drawLineSegment(

    start: Offset,
    end: Offset,
    color: Color,
    strokeWidth: Float = 7.dp.toPx(),
    cornerRadius: Float = 10.dp.toPx()

): Double {

    val p = computePath(
        start.toVector2D(),
        end.toVector2D()
    )
    val points = p.first

    if (points.isEmpty()) return p.second

    if(points.size == 2) {
        drawLine(
            color,
            start,
            end,
            strokeWidth
        )
        return p.second
    }

    val A = points[0].toOffset()
    val C = points[1].toOffset()
    val B = points[2].toOffset()

    val AC = C-A
    val CB = C-B

    val lenAC = AC.getDistance()
    val lenCB = CB.getDistance()
    val cr = cornerRadius.coerceAtMost(min(lenAC, lenCB))


    val dirAC = AC/lenAC
    val dirCB = CB/lenCB

    val E = C - dirAC * cr
    val F = C - dirCB * cr

    val path = Path().apply {
        moveTo(A.x, A.y)
        lineTo(E.x, E.y)
        quadraticTo(C.x, C.y, F.x, F.y)
        lineTo(B.x, B.y)
    }

    drawPath(
        path = path,
        color = color,
        style = Stroke(width = strokeWidth)
    )
    return p.second
}


fun DrawScope.drawTHandle(
    end: Offset,
    color: Color,
    direction: Double,        // multiple of 45
    strokeWidth: Float = 7.dp.toPx(),
    tLength: Float = 12.dp.toPx()
) {
    val theta = direction // convert direction to radians

    // Unit vector in main direction
    val dir = Offset(cos(theta).toFloat(), sin(theta).toFloat())

    // Perpendicular vector for the T cross
    val perp = Offset(-dir.y, dir.x)

    // Compute the three points of the T
    val left = end + perp * tLength      // first side of cross
    val right = end - perp * tLength     // other side of cross

    val path = Path().apply {
        moveTo(end.x, end.y)             // main line
        lineTo(left.x, left.y)           // first cross line
        moveTo(end.x, end.y)
        lineTo(right.x, right.y)         // second cross line
    }

    drawPath(
        path = path,
        color = color,
        style = Stroke(width = strokeWidth)
    )
}


fun DrawScope.drawLineEnd(
    start: Offset,
    end: Offset,
    color: Color,
    strokeWidth: Float = 7.dp.toPx(),
    cornerRadius: Float = 10.dp.toPx()
) {
    val dir = drawLineSegment(
        start,
        end,
        color,
        strokeWidth,
        cornerRadius
    )

    drawTHandle(
        end,
        color,
        dir,
        strokeWidth
    )
}