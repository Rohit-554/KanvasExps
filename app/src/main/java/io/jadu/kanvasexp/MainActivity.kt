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
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KanvasExpTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding))
                    {
                        ShapesAnimation()
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

/**
 * Created by JADU @author(Rohit-554) on 24/02/2025.
 * */
@Composable
fun ShapesAnimation(){
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black)
    ) {
        MorphingAnimation()
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