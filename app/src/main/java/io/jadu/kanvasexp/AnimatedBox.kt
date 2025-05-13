package io.jadu.kanvasexp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedBoxExample() {
    var isExpanded by remember { mutableStateOf(false) }

    // Animate color
    val backgroundColor by animateColorAsState(
        targetValue = if (isExpanded) Color(0xFF64B5F6) else Color(0xFF81C784),
        animationSpec = tween(durationMillis = 500),
        label = "Background Color Animation"
    )

    // Animate size
    val size by animateDpAsState(
        targetValue = if (isExpanded) 200.dp else 100.dp,
        animationSpec = tween(durationMillis = 500),
        label = "Size Animation"
    )

    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickable { isExpanded = !isExpanded }
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isExpanded) "Tap to Shrink" else "Tap to Expand",
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}
