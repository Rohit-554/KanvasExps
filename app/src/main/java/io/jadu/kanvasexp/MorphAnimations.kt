package io.jadu.kanvasexp


import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.Morph
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath

@Composable
fun MorphingAnimation() {

    val shapeCircle = remember {
        RoundedPolygon(
            numVertices = 4,
            radius = 1f,
            rounding = CornerRounding(1f)
        )
    }
    val shapeB = remember {
        RoundedPolygon(
            numVertices = 4,
            radius = 1f,
            rounding = CornerRounding(0.6f)
        )
    }
    val shapeA = remember {
        RoundedPolygon(
            numVertices = 6,
            radius = 1f,
            rounding = CornerRounding(0.2f)
        )
    }
    val shapeC = remember {
        RoundedPolygon(
            12,
            rounding = CornerRounding(0.4f)
        )
    }
    val roundedRectangleShape = remember {
        RoundedPolygon(
            numVertices = 4,
            radius = 1f,
            rounding = CornerRounding(0.2f)
        )
    }

    val shapes = listOf(shapeCircle, shapeA, shapeB, shapeC, roundedRectangleShape)
    val infiniteTransition = rememberInfiniteTransition("shapeMorph")
    val animateProgress = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = shapes.size.toFloat(),
        animationSpec = infiniteRepeatable(
            tween(shapes.size * 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shapeMorph"
    )

    val progressValue = animateProgress.value
    val currentIndex = progressValue.toInt() % shapes.size
    val nextIndex = (currentIndex + 1) % shapes.size
    val morphProgress = progressValue - currentIndex

    val morph = Morph(shapes[currentIndex], shapes[nextIndex])
    val animateRotation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(
                6000, easing = LinearEasing,
            ),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse,
        ),
        label = "shapeRotation"
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .size(400.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clip(
                    CustomRotatingMorphShape(
                        morph,
                        morphProgress,
                        animateRotation.value
                    )
                )
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFACC2EF),
                            Color(0xFFFCF5E3)
                        ),
                        start = Offset(animateProgress.value * 400.dp.value, 0f),
                        end = Offset(0f, animateProgress.value * 400.dp.value)
                    )
                )
                .size(400.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${progressValue}",
                fontFamily = FontFamily.Monospace,
                color = Color.Black,
                fontWeight = FontWeight.Black,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

class CustomRotatingMorphShape(
    private val morph: Morph,
    private val percentage: Float,
    private val rotation: Float
) : Shape {

    private val matrix = Matrix()
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        matrix.scale(size.width / 2f, size.height / 2f)
        matrix.translate(1f, 1f)
        matrix.rotateZ(rotation)

        val path = morph.toPath(progress = percentage).asComposePath()
        path.transform(matrix)

        return Outline.Generic(path)
    }
}