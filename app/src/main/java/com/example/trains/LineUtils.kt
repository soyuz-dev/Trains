package com.example.trains

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
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

    if (abs(det) < Vector2D.EPSILON) {
        return null // parallel or nearly parallel
    }

    val t = w.cross(v) / det
    return A + u * t
}


fun computePath(A: Vector2D, B: Vector2D): List<Vector2D> {
    val d = B - A
    val angle = atan2(d.y, d.x)

    // Straight-line snap
    val snapped = round(angle / (Math.PI / 4)) * (Math.PI / 4)
    if (abs(angle - snapped) < 1e-6) {
        return listOf(A, B)
    }

    val (a1, a2) = snapDirections(angle)
    val thetaA = a1

    val (b1, b2) = snapDirections(angle + PI)

    val thetaB = b2

    val u = Vector2D(cos(thetaA), sin(thetaA))
    val v = Vector2D(cos(thetaB), sin(thetaB))

    val C: Vector2D = intersectRays(A, u, B, v)
        ?: return listOf(A, B)

    return listOf(A, C, B)
}



fun DrawScope.drawLineSegment(

    start: Offset,
    end: Offset,
    color: Color,
    strokeWidth: Float,
    cornerRadius: Float = 10.dp.toPx()

) {

    val points = computePath(
        start.toVector2D(),
        end.toVector2D()
    )

    if (points.isEmpty()) return

    if(points.size == 2) {
        drawLine(
            color,
            start,
            end,
            strokeWidth
        )
        return
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
}


