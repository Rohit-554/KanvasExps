package io.jadu.kanvasexp


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt


enum class CalorieSummaryType {
    CALORIE_TREND,
    WEIGHT_TREND,
    DEFICIT_TREND,
    BMI_TREND
}

@Composable
fun SummaryPage() {
    Scaffold(
        topBar = {
            TrackerTopAppBar(
                title = "Analytics",
            )
        },
        containerColor = Color.LightGray
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier.padding(16.dp),
                shape = RoundedCornerShape(10f),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "🤩",
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            "You're doing amazing! ✨",
                        )
                        /*ImageComponent(
                            imageRes = Res.drawable.food_route,
                            contentDescription = "Twinkling Stars"
                        )*/
                    }
                    Row {
                        Text("Jan 1 - Today")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 28.dp, horizontal = 24.dp)
                    ) {
                        CustomProgressBar(
                            progress = 0.6f
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                CalorieSummarySection()
            }

        }

    }
}

@Preview
@Composable
fun MedicineTrackerPreview() {
    SummaryPage()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackerTopAppBar(
    title: String,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.secondary
        ),
        title = {
            Text(title)
        },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    Icons.Rounded.ArrowBack,
                    "Back",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        },
        actions = {
            /* AnalyticsTopBarButton(
                 modifier = Modifier.padding(end = 16.dp),
                 route = route
             )*/
        }
    )
}


@Composable
fun CustomProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = ProgressIndicatorDefaults.linearColor,
    trackColor: Color = Color.Gray,
    thumbColor: Color = Color(0xff28745E),
    thumbSize: Dp = 16.dp,
    height: Dp = 8.dp
) {
    var parentWidth by remember { mutableStateOf(0) }
    val screenSize = LocalDensity.current.density
    Box(
        modifier = modifier
            .height(thumbSize * 2)
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                parentWidth = coordinates.size.width
            }
    ) {
        LinearProgressIndicator(
            progress = { progress },
            color = Color.Unspecified,
            trackColor = trackColor,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(height)
                .drawWithCache {
                    val brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF4CAF50),
                            Color(0xFF2196F3)
                        )
                    )
                    onDrawBehind {
                        val strokeWidth = screenSize
                        val newProgress = progress * size.width

                        // Draw the progress line with gradient
                        drawLine(
                            brush = brush,
                            start = Offset(0f, size.height / 2),
                            end = Offset(newProgress, size.height / 2),
                            strokeWidth = strokeWidth,
                            cap = StrokeCap.Round
                        )
                    }
                }
        )

        if (parentWidth > 0) {
            val thumbOffset = with(LocalDensity.current) {
                (progress * (parentWidth - thumbSize.toPx())).roundToInt()
            }

            Box(
                modifier = Modifier
                    .offset { IntOffset(thumbOffset, 0) }
                    .align(Alignment.CenterStart)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val size = 80.dp
                    val textMeasurer = rememberTextMeasurer()
                    Canvas(
                        modifier = Modifier
                            .size(size)
                            .offset(y = (-size / 3), x = (-size / 4))
                    ) {
                        val paddingSize = (size.toPx() * 0.05.dp.toPx())
                        drawRoundRect(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xff28745E), Color(0xff28745E)),
                                startX = 0f,
                                endX = size.toPx()
                            ),
                            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
                            style = Fill,
                            size = Size(41.dp.toPx(), 20.dp.toPx())
                        )
                        drawText(
                            textMeasurer = textMeasurer,
                            text = "${(progress * 100).toInt()}%",
                            topLeft = Offset(30f, 8f),
                            style = TextStyle(fontSize = 12.sp, color = Color.White)
                        )

                        val path = Path().apply {
                            moveTo((size / 4).toPx(), ((size / 2).toPx() - paddingSize))
                            lineTo(0f + (size.toPx() * 0.05.dp.toPx()), (size / 4).toPx())
                            lineTo(
                                (size.toPx() / 2) - (size.toPx() * 0.05.dp.toPx()),
                                (size / 4).toPx()
                            )
                            lineTo((size / 4).toPx(), ((size / 2).toPx() - paddingSize))

                            close()
                        }
                        drawPath(
                            path = path,
                            color = Color(0xff28745E),
                            style = Fill
                        )
                        drawPath(
                            path = path,
                            color = Color(0xff28745E),
                            style = Stroke(width = 2.dp.toPx())
                        )
                    }
                }
            }

            // Thumb indicator
            Box(
                modifier = Modifier
                    .offset { IntOffset(thumbOffset, 0) }
                    .size(10.dp)
                    .background(thumbColor, CircleShape)
                    .align(Alignment.CenterStart)
            )
        }
    }
}

@Composable
fun CalorieSummarySection() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(10) { photo ->
                CalorieSummaryCard(
                    title = "Calorie Trend",
                    subtitle = "Lat 7 days",
                    image = "https://www.google.com",
                    analyticData = "2000",
                    calorieSummaryType = CalorieSummaryType.CALORIE_TREND
                )
            }
        },
    )
}

@Composable
fun CalorieSummaryCard(
    title: String,
    subtitle: String,
    image: String,
    analyticData: String,
    calorieSummaryType: CalorieSummaryType
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    title,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Icon(
                    modifier = Modifier.size(24.dp).align(Alignment.CenterEnd),
                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                    contentDescription = "Calorie Trend",
                    tint = Color(0xff28745E)
                )
            }

            Text(subtitle)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    imageVector = Icons.Rounded.Done,
                    contentDescription = "Calorie Trend",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
            when (
                calorieSummaryType
            ) {
                CalorieSummaryType.CALORIE_TREND -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                "2200/1900"
                            )
                            Text(
                                "Consumed"
                            )
                        }
                        Column {
                            Text(
                                "200/500"
                            )
                            Text(
                                "burned"
                            )
                        }
                    }
                }

                CalorieSummaryType.WEIGHT_TREND -> {
                    Column {
                        Row {
                            Text(
                                "1.2"
                            )
                            Text(
                                "kg"
                            )
                        }
                        Row {
                            Text(
                                "loosing per week"
                            )
                        }
                    }
                }

                CalorieSummaryType.DEFICIT_TREND -> {
                    Column {
                        Row {
                            Text(
                                "100/"
                            )
                            Text(
                                "300"
                            )
                            Text(
                                "kcals"
                            )
                        }
                        Row {
                            Text(
                                "you are in surplus"
                            )
                        }
                    }
                }

                CalorieSummaryType.BMI_TREND -> {
                    Column {
                        Row {
                            Text(
                                "28.1"
                            )
                            Text(
                                "->"
                            )
                            Text(
                                "20.5"
                            )
                        }
                        Text(
                            "In overweight range"
                        )
                    }


                }
            }
        }
    }
}