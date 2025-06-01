package io.jadu.kanvasexp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun AnimatedScreen() {
    var boxMoved by remember { mutableStateOf(false) }
    var textVisible by remember { mutableStateOf(false) }

    // Animate box offset
    val offset by animateDpAsState(
        targetValue = if (boxMoved) 200.dp else 0.dp,
        animationSpec = tween(durationMillis = 1000)
    )

    // Trigger animations with delay
    LaunchedEffect(Unit) {
        delay(500)
        boxMoved = true
        delay(1200)
        textVisible = true
    }

    // UI Layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .offset(x = offset)
                    .size(100.dp)
                    .background(Color.Cyan, shape = RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(visible = textVisible) {
                Text(
                    text = "Welcome to Animated Screen!",
                    color = Color.White,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}