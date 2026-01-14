package com.example.trains


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.changedToDown
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.trains.drawings.drawLineEnd
import com.example.trains.drawings.drawOutlinedCircle
import com.example.trains.ui.theme.TrainsTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrainsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { padding -> Game(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) }
            }
        }
    }
}


@Composable
fun Game(modifier: Modifier = Modifier) {

    val scale = remember {Animatable(0f)}

    val grabRadius = 30.dp

    var dragging by remember { mutableStateOf(false) }

    var tapLocation by remember {mutableStateOf<Offset>(Offset(50f,70f))}

    var canvasCenter by remember {mutableStateOf<Offset>(Offset(0f,0f))}

    LaunchedEffect(Unit) {
        delay(2000)
        scale.animateTo(
            targetValue = 1f,
            spring(
                0.6f,
                1400f
            ),
            )
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val change = event.changes.firstOrNull() ?: continue

                        if(change.changedToDown()) {
                            val distance = (change.position-tapLocation).getDistance()
                            dragging = distance <= grabRadius.toPx()
                        }

                        if (change.pressed && dragging) {
                            tapLocation = change.position
                        }

                        if (change.changedToUp()) {
                            dragging = false
                            tapLocation = canvasCenter
                        }

                        change.consume()
                    }
                }
            }

    ) {
        canvasCenter = center
        drawLineEnd(
            center,
            tapLocation,
            Color(0xffff5d52),
            7.dp.toPx()
        )
        
        drawOutlinedCircle(
            center,
            18.dp.toPx()*scale.value,
            fillColor = Color(0xffa8a8a8),
            strokeColor = Color(0xff585858),
            strokeWidth = 6.dp.toPx()*scale.value,
        )
    }
}


