package io.jadu.kanvasexp

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircularProgressBar(
    percentage: Float,
    radius: Dp = 100.dp,
    color: Color = Color.Blue,
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 1000,
    showPercentage: Boolean = true
) {
    val currentPercentage by animateFloatAsState(
        targetValue = percentage,
        animationSpec = tween(
            durationMillis = animDuration
        ),
        label = "progress"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2f)
    ) {
        Canvas(
            modifier = Modifier.size(radius * 2f)
        ) {
            drawCircle(
                color = color.copy(alpha = 0.2f),
                radius = size.width / 2 - strokeWidth.toPx() / 2,
                style = Stroke(width = strokeWidth.toPx())
            )

            val sweepAngle = (currentPercentage * 360f) / 100f

            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(
                    width = strokeWidth.toPx(),
                    cap = StrokeCap.Round
                )
            )
        }

        if (showPercentage) {
            Text(
                text = "${currentPercentage.toInt()}%",
                color = color
            )
        }
    }
}

@Composable
fun ProgressBarDemo() {
    CircularProgressBar(
        percentage = 75f,
        radius = 80.dp,
        color = Color.Blue,
        strokeWidth = 8.dp,
        animDuration = 1000
    )
}