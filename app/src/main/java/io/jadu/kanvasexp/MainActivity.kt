package io.jadu.kanvasexp

import android.graphics.RuntimeShader
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.Morph
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import io.jadu.kanvasexp.ui.theme.KanvasExpTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.round
import kotlin.math.sin
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import io.jadu.kanvasexp.burpy.HealthConnectScreen
import io.jadu.kanvasexp.burpy.healthConnect.HealthConnectPermissionScreen
import kotlin.random.Random



class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KanvasExpTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.Black
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding))
                    {
                        HealthConnectPermissionScreen()
                        //HealthConnectScreen()
                        //KawaiiCloudWithRainbow()
                        //HolographicGalaxy()
                       // AnimatedHeart()
                       /* ButtonComponent(
                            onClick = {},
                            title = "Add manually",
                            config = ButtonConfigurations(
                                modifier = Modifier.border(0.7.dp, Color.Black, RoundedCornerShape(24f)).padding(4.dp),
                                background = Color.Transparent,
                                foreground = Color.Cyan,
                                textProps = TextProps(
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                ),
                            )
                        )*/

                        //Pager()
                        //HolographicEnergyOrb()
                        //AnimatedHeartShape()
                        //button()
                        /*Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(10.dp))
                                .height(48.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    text = "insideBoxLeftTitle",
                                )
                                Row(
                                    modifier = Modifier
                                        .background(Color.Gray, shape = RoundedCornerShape(10f))
                                        .height(48.dp)
                                        .padding(horizontal = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(start = 8.dp)
                                    ) {
                                        Text(
                                            text = "dropDownValue",
                                        )

                                        Image(
                                            imageVector = Icons.Default.KeyboardArrowDown,
                                            contentDescription = "Arrow down",
                                        )
                                    }
                                }
                            }
                        }*/


                        //boxUnitTest()
                        //ShapesAnimation()
                        //Drawing()
                        //TunnelShader()
                        //FractalShader()
                        //GradientsAndShaders()
                        //Kanvas()
                        //QuadraticBezierCurve()
                    }
                }
            }
        }
    }
}


@Composable
fun KawaiiCloudWithRainbow() {
    // Animation for cloud bounce
    val infiniteTransition = rememberInfiniteTransition()
    val cloudBounce by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Animation for star twinkle
    val starTwinkle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val centerX = size.width / 2
        val centerY = size.height / 2

        // Draw the cloud
        drawCircle(
            color = Color.White,
            radius = 150f,
            center = Offset(centerX, centerY + cloudBounce)
        )
        drawCircle(
            color = Color.White,
            radius = 120f,
            center = Offset(centerX - 100f, centerY + 50f + cloudBounce)
        )
        drawCircle(
            color = Color.White,
            radius = 120f,
            center = Offset(centerX + 100f, centerY + 50f + cloudBounce)
        )
        drawCircle(
            color = Color.White,
            radius = 100f,
            center = Offset(centerX - 150f, centerY + 100f + cloudBounce)
        )
        drawCircle(
            color = Color.White,
            radius = 100f,
            center = Offset(centerX + 150f, centerY + 100f + cloudBounce)
        )

        // Draw the face
        drawArc(
            color = Color.Black,
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(centerX - 50f, centerY + 50f + cloudBounce),
            size = Size(100f, 50f),
            style = Stroke(width = 4f)
        )
        drawCircle(
            color = Color.Black,
            radius = 10f,
            center = Offset(centerX - 30f, centerY + 20f + cloudBounce)
        )
        drawCircle(
            color = Color.Black,
            radius = 10f,
            center = Offset(centerX + 30f, centerY + 20f + cloudBounce)
        )

        // Draw the rainbow
        val rainbowColors = listOf(
            Color.Red,
            Color(0xFFFFA500),
            Color.Yellow,
            Color.Green,
            Color.Blue,
            Color(0xFF4B0082),
            Color(0xFF8A2BE2)
        )
        val rainbowWidth = 20f
        var currentRadius = 200f
        rainbowColors.forEach { color ->
            drawArc(
                color = color,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(centerX - currentRadius, centerY - currentRadius + cloudBounce),
                size = Size(currentRadius * 2, currentRadius * 2),
                style = Stroke(width = rainbowWidth)
            )
            currentRadius += rainbowWidth
        }

        // Draw the stars stars
        val starPositions = listOf(
            Offset(centerX - 200f, centerY - 200f),
            Offset(centerX + 250f, centerY - 150f),
            Offset(centerX - 300f, centerY - 100f),
            Offset(centerX + 350f, centerY - 50f)
        )
        starPositions.forEach { position ->
            drawStar(
                position = position,
                size = 30f,
                color = Color.Yellow.copy(alpha = starTwinkle)
            )
        }
    }
}

// Function to draw a star
fun DrawScope.drawStar(
    position: Offset,
    size: Float,
    color: Color
) {
    val starPath = Path().apply {
        val outerRadius = size
        val innerRadius = size / 2
        val spikes = 5
        var rotation = Math.PI / 2 * 3
        val step = Math.PI / spikes

        moveTo(
            x = position.x + (outerRadius * cos(rotation)).toFloat(),
            y = position.y + (outerRadius * sin(rotation)).toFloat()
        )

        for (i in 0 until spikes) {

            val x = position.x + (outerRadius * cos(rotation)).toFloat()
            val y = position.y + (outerRadius * sin(rotation)).toFloat()
            lineTo(x, y)
            rotation += step

            val x2 = position.x + (innerRadius * cos(rotation)).toFloat()
            val y2 = position.y + (innerRadius * sin(rotation)).toFloat()
            lineTo(x2, y2)
            rotation += step
        }
        close()
    }
    drawPath(starPath, color)
}


@Composable
fun HolographicGalaxy() {
    // Particle data
    data class Particle(
        var x: Float,
        var y: Float,
        var z: Float,
        var radius: Float,
        var color: Color,
        var trail: List<Offset>
    )

    // State for particles
    val particles = remember { mutableStateListOf<Particle>() }
    var rotationX by remember { mutableStateOf(0f) }
    var rotationY by remember { mutableStateOf(0f) }

    // Animation for color shift
    val infiniteTransition = rememberInfiniteTransition()
    val colorShift by infiniteTransition.animateColor(
        initialValue = Color.Cyan,
        targetValue = Color.Magenta,
        animationSpec = infiniteRepeatable(
            tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Create particles
    LaunchedEffect(Unit) {
        repeat(500) { // Create 500 stars
            particles.add(
                Particle(
                    x = Random.nextFloat() * 1000.dp.value,
                    y = Random.nextFloat() * 1000.dp.value,
                    z = Random.nextFloat() * 1000.dp.value,
                    radius = Random.nextFloat() * 2 + 1,
                    color = Color.White,
                    trail = emptyList()
                )
            )
        }
        repeat(10) { // Create 10 planets
            particles.add(
                Particle(
                    x = Random.nextFloat() * 1000.dp.value,
                    y = Random.nextFloat() * 1000.dp.value,
                    z = Random.nextFloat() * 1000.dp.value,
                    radius = Random.nextFloat() * 10 + 5,
                    color = Color.Yellow,
                    trail = List(10) { Offset(0f, 0f) }
                )
            )
        }
    }

    // Canvas to draw particles
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, _, _ ->
                    rotationX += pan.x * 0.01f
                    rotationY += pan.y * 0.01f
                }
            }
    ) {
        // Clear canvas
        drawRect(Color.Black, size = size)

        // Draw particles
        particles.forEach { particle ->
            // Apply 3D rotation
            val cosX = cos(rotationX)
            val sinX = sin(rotationX)
            val cosY = cos(rotationY)
            val sinY = sin(rotationY)

            val rotatedY = particle.y * cosX - particle.z * sinX
            val rotatedZ = particle.y * sinX + particle.z * cosX
            val rotatedX = particle.x * cosY - rotatedZ * sinY
            val finalZ = particle.x * sinY + rotatedZ * cosY

            // Perspective projection
            val scale = 1000f / (finalZ + 1000f)
            val x = (rotatedX * scale) + center.x
            val y = (rotatedY * scale) + center.y

            // Draw particle
            drawCircle(
                color = particle.color,
                radius = particle.radius * scale,
                center = Offset(x, y)
            )

            // Draw planet trails
            if (particle.trail.isNotEmpty()) {
                val trailPath = Path().apply {
                    moveTo(x, y)
                    particle.trail.forEach { trailPoint ->
                        lineTo(trailPoint.x, trailPoint.y)
                    }
                }
                drawPath(
                    path = trailPath,
                    color = particle.color.copy(alpha = 0.5f),
                    style = Stroke(width = 2f)
                )
            }
        }

        // Draw glowing galaxy core
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(colorShift.copy(alpha = 0.5f), colorShift.copy(alpha = 0f)),
                center = center,
                radius = 200f
            ),
            radius = 200f,
            center = center,
            blendMode = BlendMode.Plus
        )
    }
}

@Composable
fun HolographicEnergyOrb() {
    // Particle data
    data class Particle(
        var position: Offset,
        var velocity: Offset,
        var color: Color,
        var radius: Float,
        var alpha: Float
    )

    // State for particles
    val particles = remember { mutableStateListOf<Particle>() }
    val touchPoint = remember { mutableStateOf<Offset?>(null) }

    // Animation for color shift
    val infiniteTransition = rememberInfiniteTransition()
    val colorShift by infiniteTransition.animateColor(
        initialValue = Color.Cyan,
        targetValue = Color.Magenta,
        animationSpec = infiniteRepeatable(
            tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Create particles
    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos { frameTime ->
                if (particles.size < 500) { // Limit number of particles
                    particles.add(
                        Particle(
                            position = Offset(
                                Random.nextFloat() * 1000.dp.value,
                                Random.nextFloat() * 1000.dp.value
                            ),
                            velocity = Offset(
                                Random.nextFloat() * 2 - 1,
                                Random.nextFloat() * 2 - 1
                            ),
                            color = Color.White,
                            radius = Random.nextFloat() * 3 + 2,
                            alpha = 1f
                        )
                    )
                }

                // Update particle positions and alpha
                particles.forEach { particle ->
                    // Attract particles to touch point
                    touchPoint.value?.let { touch ->
                        val direction = (touch - particle.position).normalize() * 0.1f
                        particle.velocity += direction
                    }

                    // Move particles
                    particle.position += particle.velocity

                    // Fade particles over time
                    particle.alpha = (particle.alpha - 0.005f).coerceAtLeast(0f)

                    // Reset particles that fade out
                    if (particle.alpha <= 0) {
                        particle.position = Offset(
                            Random.nextFloat() * 1000.dp.value,
                            Random.nextFloat() * 1000.dp.value
                        )
                        particle.alpha = 1f
                    }
                }

                // Remove dead particles
                particles.removeAll { it.alpha <= 0 }
            }
        }
    }

    // Canvas to draw particles
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { touch ->
                    touchPoint.value = touch
                }
            }
    ) {
        // Draw particles
        particles.forEach { particle ->
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(colorShift, colorShift.copy(alpha = 0.5f)),
                    center = particle.position,
                    radius = particle.radius * 10
                ),
                radius = particle.radius,
                center = particle.position,
                alpha = particle.alpha
            )
        }

        // Draw glowing orb effect
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(colorShift.copy(alpha = 0.3f), colorShift.copy(alpha = 0f)),
                center = touchPoint.value ?: center,
                radius = 200f
            ),
            radius = 200f,
            center = touchPoint.value ?: center,
            blendMode = BlendMode.Plus
        )
    }
}

fun Offset.normalize(): Offset {
    val length = this.getDistance()
    return if (length != 0f) {
        Offset(this.x / length, this.y / length)
    } else {
        Offset.Zero
    }
}

@Composable
fun AnimatedHeart() {
    // State to control the animation
    var isAnimating by remember { mutableStateOf(false) }

    // Animate the scale of the heart
    val scale by animateFloatAsState(
        targetValue = if (isAnimating) 1.5f else 1f,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Canvas to draw the heart
        Canvas(
            modifier = Modifier
                .size(200.dp)
                .scale(scale)
        ) {
            val width = size.width
            val height = size.height

            val path = Path().apply {
                // Draw the heart shape
                moveTo(width / 2, height / 5)
                cubicTo(
                    width / 4, 0f,
                    0f, height / 3,
                    width / 2, height * 2 / 3
                )
                cubicTo(
                    width, height / 3,
                    width * 3 / 4, 0f,
                    width / 2, height / 5
                )
                close()
            }

            // Draw the heart with a red color
            drawPath(path, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to start/stop the animation
        Button(onClick = { isAnimating = !isAnimating }) {
            Text(if (isAnimating) "Stop Animation" else "Start Animation")
        }
    }
}

@Composable
fun AnimatedHeartShape() {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val constraints = constraints
        var clicked by remember { mutableStateOf(false) }
        val heartSize = 400.dp
        val heartSizePx = with(LocalDensity.current) { heartSize.toPx() }

        val infiniteTransition =
            rememberInfiniteTransition(label = "Sample rememberInfiniteTransition")
        val animatedStrokePhase = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = heartSizePx * 2,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ), label = "Sample AnimatedFloat"
        )

        val continuousScale = infiniteTransition.animateFloat(
            initialValue = 0.8f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(500, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ), label = "Sample AnimatedFloat"
        )

        val disappearScale by animateFloatAsState(
            targetValue = if (clicked) 0f else 1f,
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearOutSlowInEasing
            ), label = "Sample Float As State"
        )

        Canvas(
            modifier = Modifier
                .size(heartSize)
                .clickable { clicked = true }
        ) {
            val path = Path().apply {
                moveTo(heartSizePx / 2, heartSizePx / 5)
                cubicTo(
                    heartSizePx * 3 / 4,
                    0f,
                    heartSizePx,
                    heartSizePx / 3,
                    heartSizePx / 2,
                    heartSizePx
                )
                cubicTo(0f, heartSizePx / 3, heartSizePx / 4, 0f, heartSizePx / 2, heartSizePx / 5)
                close()
            }

            val gradient = Brush.linearGradient(
                colors = listOf(Color.Red, Color(0xFF841C26), Color(0xFFBA274A)),
                start = Offset(0f, 0f),
                end = Offset(heartSizePx, heartSizePx)
            )

            val combinedScale = continuousScale.value * disappearScale

            scale(combinedScale, combinedScale, pivot = Offset(heartSizePx / 2, heartSizePx / 2)) {
                drawPath(path, gradient)

                val lineGradient = Brush.linearGradient(
                    colors = listOf(Color(0xFFFFE45E), Color(0xFFFF6392)),
                    start = Offset(0f, 0f),
                    end = Offset(heartSizePx, heartSizePx)
                )

                val pathEffect = PathEffect.dashPathEffect(
                    intervals = floatArrayOf(heartSizePx, heartSizePx),
                    phase = -animatedStrokePhase.value
                )

                drawPath(
                    path = path,
                    brush = lineGradient,
                    style = Stroke(width = 8.dp.toPx(), pathEffect = pathEffect)
                )
            }
        }
    }
}

/**
 * Created by JADU @author(Rohit-554) on 24/02/2025.
 * */
@Composable
fun ShapesAnimation() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        MorphingAnimation()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Pager() {
    val pagerState = rememberPagerState(initialPage = 0) { 4 }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Page: $page",
                        color = Color.Black, // ✅ Ensure text is visible
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // ✅ Remove BurpyPagerIndicator temporarily to debug
        BurpyPagerIndicator(pagerState = pagerState)

        Spacer(modifier = Modifier.height(16.dp)) // Optional spacing
    }
}


@Composable
fun button() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.weight(0.8f)
        ) {
            Text(text = "Button")
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.weight(0.2f)
        ) {
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Add"
            )
        }
    }
}


@Composable
fun FancyCanvasUI(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val animatedRadius by infiniteTransition.animateFloat(
        initialValue = 50f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(
                colors = listOf(Color(0xFF0F2027), Color(0xFF2C5364))
            ))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2)

            // Outer gradient circle
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color.Cyan, Color.Transparent),
                    center = center,
                    radius = animatedRadius * 2
                ),
                center = center,
                radius = animatedRadius * 2
            )

            // Inner glowing circle
            drawCircle(
                color = Color.Magenta.copy(alpha = 0.7f),
                radius = animatedRadius,
                center = center,
                style = Fill
            )

            // Shadow Circle
            drawCircle(
                color = Color.White.copy(alpha = 0.2f),
                center = center + Offset(100f, 100f),
                radius = 30f,
                style = Fill
            )

            // Decorative lines
            for (i in 0..360 step 30) {
                val angleRad = Math.toRadians(i.toDouble())
                val startX = center.x + cos(angleRad) * 120
                val startY = center.y + sin(angleRad) * 120
                val endX = center.x + cos(angleRad) * 150
                val endY = center.y + sin(angleRad) * 150

                drawLine(
                    color = Color.White.copy(alpha = 0.3f),
                    start = Offset(startX.toFloat(), startY.toFloat()),
                    end = Offset(endX.toFloat(), endY.toFloat()),
                    strokeWidth = 3f
                )
            }
        }

        Text(
            text = "Galaxy Canvas",
            style = TextStyle(
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                shadow = Shadow(Color.Black, offset = Offset(2f, 2f), blurRadius = 5f)
            ),
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 50.dp)
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun BurpyPagerIndicator(
    modifier: Modifier = Modifier,
    activeIndex: Int = 0,
    totalItems: Int = 0,
    activeColor: Color = Color.White,
    inactiveColor: Color = activeColor.copy(alpha = 1f),
    space: Dp = 8.dp,
    activeWidth: Dp = 38.dp,
    inactiveWidth: Dp = 16.dp,
    indicatorHeight: Dp = 16.dp,
    textColor: Color = Color.Black,
    pagerState: PagerState? = null,
    showText: Boolean = true
) {
    val actualActiveIndex = pagerState?.currentPage ?: activeIndex
    val actualTotalItems = pagerState?.pageCount ?: totalItems

    Row(
        modifier = modifier
            .padding(vertical = 4.dp)
            .wrapContentWidth(Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (index in 0 until actualTotalItems) {
            val width by animateDpAsState(
                targetValue = if (index == actualActiveIndex) activeWidth else inactiveWidth,
                animationSpec = tween(durationMillis = 300), label = ""
            )
            val backgroundColor by animateColorAsState(
                targetValue = if (index == actualActiveIndex) activeColor else inactiveColor,
                animationSpec = tween(durationMillis = 300),
                label = "" // Animation duration of 1 second
            )

            if (index > 0) {
                Spacer(modifier = Modifier.width(space)) // Gap between items
            }

            Box(
                modifier = Modifier
                    .height(indicatorHeight)
                    .width(width)
                    .clip(RoundedCornerShape(indicatorHeight / 2))
                    .background(backgroundColor)

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    AnimatedVisibility(
                        visible = index == actualActiveIndex,
                        enter = scaleIn(
                            animationSpec = tween(
                                durationMillis = 300,
                                delayMillis = 60, // 1000 milliseconds delay
                            )
                        ),
                        exit = scaleOut(),
                    ) {
                        if (showText)
                            Text(
                                text = "${index + 1}/$actualTotalItems",
                                color = textColor,
                                textAlign = TextAlign.Center,
                                style = TextStyle(fontSize = 14.sp),
                                modifier = Modifier.graphicsLayer {
                                    translationY = -2f
                                },
                            )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun SimpleComposablePreview() {
    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        BurpyPagerIndicator(
            activeIndex = 5,
            totalItems = 6,
        )
    }
}


@Composable
fun boxUnitTest() {
    Column {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(16.dp)
                )
                .border(
                    2.dp, Color(0xffDDDDDD),
                    shape = RoundedCornerShape(16.dp)
                )
                .height(32.dp)
                .wrapContentSize()
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(2.dp))
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Circle",
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "12",
                    fontSize = 16.sp
                )
            }
        }

        Row {
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xffDDDDDD),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .size(32.dp)
            ) {
                Image(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(14.dp),
                    imageVector = Icons.Default.Build,
                    contentDescription = "customize",
                )
            }
            VerticalDivider(
                modifier = Modifier
                    .height(32.dp)
                    .padding(start = 6.dp)
                    .width(2.dp)
                    .background(Color(0xff000000))
            )
        }

    }


}


private const val FRACTAL_SHADER_SRC = """
    uniform float2 size;
    uniform float time;
    uniform shader composable;
    
    float f(float3 p) {
        p.z -= time * 5.;
        float a = p.z * .1;
        p.xy *= mat2(cos(a), sin(a), -sin(a), cos(a));
        return .1 - length(cos(p.xy) + sin(p.yz));
    }
    
    half4 main(float2 fragcoord) { 
        // Normalize coordinates to center of screen
        float2 uv = (fragcoord - size.xy * 0.5) / size.y;
        float3 d = float3(uv, 1.0) * 0.5;
        float3 p = float3(0);
        
        for (int i = 0; i < 32; i++) {
          p += f(p) * d;
        }
        return half4((sin(p) + float3(2, 5, 12)) / length(p), 1.0);
    }
"""

private const val TUNNEL_SHADER_SRC = """
    uniform float2 iResolution;
    uniform float iTime;
    uniform shader composable;

    float2x2 rotate2D(float r) {
        return float2x2(
            cos(r), sin(r),
            -sin(r), cos(r)
        );
    }

    float3x3 rotate3D(float angle, float3 axis) {
        float3 a = normalize(axis);
        float s = sin(angle);
        float c = cos(angle);
        float r = 1.0 - c;
        
        return float3x3(
            a.x * a.x * r + c,
            a.y * a.x * r + a.z * s,
            a.z * a.x * r - a.y * s,
            a.x * a.y * r - a.z * s,
            a.y * a.y * r + c,
            a.z * a.y * r + a.x * s,
            a.x * a.z * r + a.y * s,
            a.y * a.z * r - a.x * s,
            a.z * a.z * r + c
        );
    }

    half4 main(float2 FC) {
        float4 o = float4(0.0);
        float2 r = iResolution.xy;
        float3 v = float3(1.0, 3.0, 7.0);
        float3 p = float3(0.0);
        float t = iTime;
        float n = 0.0;
        float e = 0.0;
        float g = 0.0;
        float k = t * 0.2;

        for (int i = 0; i < 100; i++) {
            p = float3((FC.xy - r * 0.5) / r.y * g, g) * rotate3D(k, cos(k + v));
            p.z += t;
            p = asin(sin(p)) - 3.0;
            n = 0.0;
            
            for (int j = 0; j < 9; j++) {
                float2x2 rotation = rotate2D(g/8.0);
                p.xz = float2(
                    rotation[0][0] * p.x + rotation[0][1] * p.z,
                    rotation[1][0] * p.x + rotation[1][1] * p.z
                );
                p = abs(p);
                if (p.x < p.y) {
                    n += 1.0;
                    p = p.zxy;
                } else {
                    p = p.zyx;
                }
                p += p - v;
            }
            
            e = max(p.x, p.z) / 1000.0 - 0.01;
            g += e;
            o.rgb += 0.1/exp(cos(v*g*0.1+n) + 3.0 + 10000.0*e);
        }
        
        return half4(o.rgb, 1.0);
    }
"""

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun TunnelShader() {
    val shader = remember { RuntimeShader(TUNNEL_SHADER_SRC) }
    var time by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            time = (System.currentTimeMillis() % 100_000L) / 1_000f
            delay(16) // ~60 FPS
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                clip = true
                shader.setFloatUniform("iTime", time)
                shader.setFloatUniform("iResolution", size.width, size.height)
                renderEffect = android.graphics.RenderEffect
                    .createRuntimeShaderEffect(shader, "composable")
                    .asComposeRenderEffect()
            },
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize())
    }
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun FractalShader() {
    val shader = remember { RuntimeShader(FRACTAL_SHADER_SRC) }
    var time by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            time = (System.currentTimeMillis() % 100_000L) / 1_000f
            delay(16) // Approximately 60 FPS
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                clip = true
                shader.setFloatUniform("time", time)
                shader.setFloatUniform("size", size.width, size.height)
                renderEffect = android.graphics.RenderEffect
                    .createRuntimeShaderEffect(shader, "composable")
                    .asComposeRenderEffect()
            },
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize())
    }
}

/*@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ShaderEffect() {
    val shader = RuntimeShader("""
       uniform float2 resolution;
       uniform float time;
       uniform shader composable;

       float2x2 rotate2D(float r) {
           return float2x2(
               cos(r), sin(r),
               -sin(r), cos(r)
           );
       }

       float3x3 rotate3D(float angle, float3 axis) {
           float3 a = normalize(axis);
           float s = sin(angle);
           float c = cos(angle);
           float r = 1.0 - c;
           
           return float3x3(
               a.x * a.x * r + c,
               a.y * a.x * r + a.z * s,
               a.z * a.x * r - a.y * s,
               a.x * a.y * r - a.z * s,
               a.y * a.y * r + c,
               a.z * a.y * r + a.x * s,
               a.x * a.z * r + a.y * s,
               a.y * a.z * r - a.x * s,
               a.z * a.z * r + c
           );
       }

       half4 main(float2 fragCoord) {
           float4 o = float4(0.0);
           float2 r = resolution.xy;
           float3 v = float3(1.0, 3.0, 7.0);
           float3 p = float3(0.0);
           float t = time;
           float n = 0.0;
           float e = 0.0;
           float g = 0.0;
           float k = t * 0.2;

           for (int i = 0; i < 100; i++) {
               p = float3((fragCoord.xy - r * 0.5) / r.y * g, g) * rotate3D(k, cos(k + v));
               p.z += t;
               p = asin(sin(p)) - 3.0;
               n = 0.0;
               
               for (int j = 0; j < 9; j++) {
                   float2x2 rotation = rotate2D(g/8.0);
                   p.xz = float2(
                       rotation[0][0] * p.x + rotation[0][1] * p.z,
                       rotation[1][0] * p.x + rotation[1][1] * p.z
                   );
                   p = abs(p);
                   if (p.x < p.y) {
                       n += 1.0;
                       p = p.zxy;
                   } else {
                       p = p.zyx;
                   }
                   p += p - v;
               }
               
               e = max(p.x, p.z) / 1000.0 - 0.01;
               g += e;
               o.rgb += 0.1/exp(cos(v*g*0.1+n) + 3.0 + 10000.0*e);
           }
           
           return half4(o.rgb, 1.0);
       }

    """.trimIndent())

    val shader2 = RuntimeShader(FRACTAL_SHADER_SRC)
    val time = remember { mutableStateOf(0f) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch {
            while (true){
                time.value = (System.currentTimeMillis() % 100_000L) / 1_000f
                delay(10)
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize().graphicsLayer {
            clip = true
            shader2.setFloatUniform("time",time.value)
            //shader.setFloatUniform("resolution", size.width, size.height)
            renderEffect = android.graphics.RenderEffect.createRuntimeShaderEffect(shader2,"composable").asComposeRenderEffect()
        },
        color = MaterialTheme.colorScheme.background
    ){

    }

   *//* Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        shader.setFloatUniform("resolution", size.width, size.height)
        shader.setFloatUniform("time", time.value)

        drawRect(
            brush = ShaderBrush(shader),
            size = size
        )
    }*//*
}*/


@Composable
fun GradientsAndShaders() {
    val brush = Brush.linearGradient(
        colors = listOf(Color.Red, Color.Blue),
    )
    val tileSize = with(LocalDensity.current) {
        50.dp.toPx()
    }
    val listColors = listOf(Color.Yellow, Color.Red, Color.Blue)

    /* Column(
         modifier = Modifier.fillMaxSize(),
         verticalArrangement = Arrangement.Center,
         horizontalAlignment = Alignment.CenterHorizontally,
     ) {
         Canvas(
             modifier = Modifier.size(200.dp),
             onDraw = {
                 drawCircle(
                     brush
                 )
             }
         )
     }
     Box(
         modifier = Modifier
             .requiredSize(300.dp)
             .background(
                 Brush.horizontalGradient(
                     listColors,
                     endX = tileSize,
                     tileMode = TileMode.Mirror
                 )
             )
     )
 */
    //For radial gradient
    val largeRadialGradient = object : ShaderBrush() {
        override fun createShader(size: Size): Shader {
            val biggerDimension = maxOf(size.height, size.width)
            return RadialGradientShader(
                colors = listOf(Color(0xFF2be4dc), Color(0xFF243484)),
                center = size.center,
                radius = biggerDimension / 2f,
                colorStops = listOf(0f, 0.95f)
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(largeRadialGradient)
    )

}


@Composable
fun Kanvas() {
    val color = MaterialTheme.colorScheme.onSurface
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        drawCircle(
            color = color,
            radius = canvasWidth / 5,
            center = Offset(canvasWidth / 2, canvasHeight / 2),
            style = Stroke(
                width = 20f,
                pathEffect = PathEffect.chainPathEffect(
                    PathEffect.dashPathEffect(floatArrayOf(20f, 10f), 0f),
                    PathEffect.cornerPathEffect(10f)
                )
            ),
        )

        drawRect(
            color = color,
            size = Size(100f, 100f),
            topLeft = Offset((canvasWidth - 100f) / 2, (canvasHeight - 100f) / 2),

            )
    }
}


@Composable
fun Drawing() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(100.dp)
                    .background(
                        color = Color.Yellow,
                        shape = RoundedCornerShape(10f)
                    )
                    .wrapContentSize()
                    .drawWithContent {
                        drawLine(
                            color = Color.Black,
                            start = Offset(-40f, -50f),
                            end = Offset(20.dp.toPx(), -50f),
                            strokeWidth = 4f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 1f)
                        )
                    }
            )
            Spacer(modifier = Modifier.width(20.dp))
            Box(
                modifier = Modifier
                    .width(30.dp)
                    .height(100.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10f)
                    )
                    .wrapContentSize()
                    .drawWithContent {

                        drawRoundRect(
                            topLeft = Offset(-15.dp.toPx(), 12.dp.toPx()),
                            color = Color.Black,
                            size = Size(30.dp.toPx(), 100f),
                            cornerRadius = CornerRadius(10f, 10f),
                            style = Fill,
                        )
                        drawLine(
                            color = Color.Black,
                            start = Offset(-50f, -60f),
                            end = Offset(20.dp.toPx(), -60f),
                            strokeWidth = 4f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 1f)
                        )
                    }
            )
            Spacer(modifier = Modifier.width(20.dp))
            val points = listOf(
                Offset(0f, 20f),
                Offset(50f, 25f),
                Offset(100f, 35f),
                Offset(150f, 45f),
                Offset(200f, 55f),
                Offset(250f, 65f),
                Offset(300f, 70f),
                Offset(350f, 72f),
                Offset(400f, 70f),
                Offset(450f, 65f),
                Offset(500f, 55f),
                Offset(550f, 45f),
                Offset(600f, 35f),
                Offset(650f, 25f),
                Offset(700f, 20f)
            )
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(100.dp)
                    .background(Color.DarkGray)
                    .drawWithContent {
                        points.zipWithNext { current, next ->
                            val angle = atan2(next.y - current.y, next.x - current.x)

                            // Calculate circle intersection points
                            val startX = current.x + cos(angle) * 10f
                            val startY = current.y + sin(angle) * 10f
                            val endX = next.x - cos(angle) * 10f
                            val endY = next.y - sin(angle) * 10f

                            drawLine(
                                color = Color.White,
                                start = Offset(startX, startY),
                                end = Offset(endX, endY),
                                strokeWidth = 4f,
                                cap = StrokeCap.Round
                            )
                        }

                        // Draw circles
                        points.forEach { point ->
                            drawCircle(
                                color = Color.White,
                                radius = 10f,
                                center = point,
                                style = Stroke(width = 4f),
                            )
                        }
                    }
            )

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        color = Color.Transparent,  // Changed to transparent to see the bars
                        shape = RoundedCornerShape(10f)
                    )
                    .drawWithContent {
                        // Draw the horizontal line in the middle
                        drawLine(
                            color = Color.Black,
                            start = Offset(0f, size.height / 2),
                            end = Offset(size.width, size.height / 2),
                            strokeWidth = 2f
                        )

                        val barWidth = size.width / 4  // Width of each bar
                        val spacing = 0f  // No spacing between bars as per image

                        // Draw top (red) bars
                        drawRect(
                            color = Color(0xFFFF6B6B),  // Light red
                            topLeft = Offset(0f, size.height * 0.25f),
                            size = Size(barWidth, size.height * 0.25f)
                        )

                        drawRect(
                            color = Color(0xFFFF6B6B),
                            topLeft = Offset(barWidth * 2, size.height * 0.25f),
                            size = Size(barWidth, size.height * 0.25f)
                        )

                        // Draw bottom (green) bars
                        drawRect(
                            color = Color(0xFF98CE64),  // Light green
                            topLeft = Offset(barWidth, size.height * 0.5f),
                            size = Size(barWidth, size.height * 0.25f)
                        )

                        drawRect(
                            color = Color(0xFF98CE64),
                            topLeft = Offset(barWidth * 3, size.height * 0.5f),
                            size = Size(barWidth, size.height * 0.25f)
                        )
                    }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(10f)
                    )
                    .drawWithContent {
                        // Draw the horizontal line in the middle
                        drawLine(
                            color = Color.Black,
                            start = Offset(0f, size.height / 2),
                            end = Offset(size.width, size.height / 2),
                            strokeWidth = 2f
                        )

                        val barWidth = size.width / 4  // Width of each bar
                        val spacing = 0f  // No spacing between bars as per image

                        // Draw top (red) bars
                        drawRect(
                            color = Color(0xFFFF6B6B),  // Light red
                            topLeft = Offset(0f, size.height * 0.25f),
                            size = Size(barWidth, size.height * 0.25f)
                        )

                        drawRect(
                            color = Color(0xFFFF6B6B),
                            topLeft = Offset(barWidth * 2, size.height * 0.25f),
                            size = Size(barWidth, size.height * 0.25f)
                        )

                        // Draw bottom (green) bars
                        drawRect(
                            color = Color(0xFF98CE64),  // Light green
                            topLeft = Offset(barWidth, size.height * 0.5f),
                            size = Size(barWidth, size.height * 0.25f)
                        )

                        drawRect(
                            color = Color(0xFF98CE64),
                            topLeft = Offset(barWidth * 3, size.height * 0.5f),
                            size = Size(barWidth, size.height * 0.25f)
                        )
                    }
            )

        }
    }


}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KanvasExpTheme {
        Greeting("Android")
    }
}

@Composable
fun QuadraticBezierCurve(
    modifier: Modifier = Modifier,
    startPoint: Offset = Offset(50f, 200f),
    controlPoint: Offset = Offset(200f, 50f),
    endPoint: Offset = Offset(350f, 200f)
) {
    var currentStartPoint by remember { mutableStateOf(startPoint) }
    var currentControlPoint by remember { mutableStateOf(controlPoint) }
    var currentEndPoint by remember { mutableStateOf(endPoint) }
    var draggedPoint by remember { mutableStateOf<String?>(null) }

    Canvas(
        modifier = modifier
            .size(400.dp)
            .background(Color.White)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        draggedPoint = when {
                            (offset - currentStartPoint).getDistance() < 20f -> "start"
                            (offset - currentControlPoint).getDistance() < 20f -> "control"
                            (offset - currentEndPoint).getDistance() < 20f -> "end"
                            else -> null
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        when (draggedPoint) {
                            "start" -> currentStartPoint += dragAmount
                            "control" -> currentControlPoint += dragAmount
                            "end" -> currentEndPoint += dragAmount
                        }
                    },
                    onDragEnd = { draggedPoint = null }
                )
            }
    ) {
        // Draw control lines
        drawLine(
            start = currentStartPoint,
            end = currentControlPoint,
            color = Color.LightGray,
            strokeWidth = 2f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
        )
        drawLine(
            start = currentControlPoint,
            end = currentEndPoint,
            color = Color.LightGray,
            strokeWidth = 2f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
        )

        // Draw Bezier curve
        val path = Path().apply {
            moveTo(currentStartPoint.x, currentStartPoint.y)
            quadraticBezierTo(
                currentControlPoint.x, currentControlPoint.y,
                currentEndPoint.x, currentEndPoint.y
            )
        }
        drawPath(
            path = path,
            color = Color.Blue,
            style = Stroke(width = 4f)
        )

        // Draw points
        drawPoints(
            points = listOf(currentStartPoint, currentEndPoint),
            pointMode = PointMode.Points,
            color = Color.Blue,
            strokeWidth = 16f
        )
        drawCircle(
            color = Color.Red,
            radius = 8f,
            center = currentControlPoint
        )
    }
}

// Usage
@Preview
@Composable
fun PreviewBezierCurve() {
    QuadraticBezierCurve(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
}