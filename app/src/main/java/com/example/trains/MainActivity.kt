package com.example.trains


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.trains.ui.theme.TrainsTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrainsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ -> Game() }
            }
        }
    }
}


@Composable
fun Game(modifier: Modifier = Modifier) {

    val scale = remember {Animatable(0f)}

    LaunchedEffect(Unit) {
        delay(500)
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = 0.5f,
                stiffness = 1200f
            )
        )
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val r = 20.dp.toPx() * scale.value
        val w = 6.dp.toPx() * scale.value
        drawOutlinedCircle(
            center,
            r,
            Color(0xffb1b1b1),
            Color(0xff646464),
            w
        )
    }
}

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
