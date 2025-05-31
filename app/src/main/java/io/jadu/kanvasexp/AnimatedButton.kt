package io.jadu.kanvasexp

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedButton() {
    var isClicked by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isClicked) 1.1f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "ScaleAnimation"
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isClicked) Color(0xFF64B5F6) else Color(0xFF1976D2),
        animationSpec = tween(durationMillis = 300),
        label = "ColorAnimation"
    )

    Box(
        modifier = Modifier
            .padding(16.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .background(backgroundColor, shape = RoundedCornerShape(12.dp))
            .clickable {
                isClicked = !isClicked
            }
            .padding(horizontal = 24.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isClicked) "Clicked!" else "Click Me",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}
