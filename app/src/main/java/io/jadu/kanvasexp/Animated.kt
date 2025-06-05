package io.jadu.kanvasexp

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedUI() {
    var selectedTab by remember { mutableStateOf(0) }
    var isLoading by remember { mutableStateOf(false) }
    var showCards by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        showCards = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF667eea),
                        Color(0xFF764ba2)
                    )
                )
            )
    ) {
        // Animated Header
        AnimatedHeader()

        // Tab Row with Animation
        AnimatedTabRow(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        // Content based on selected tab
        when (selectedTab) {
            0 -> AnimatedCardList(showCards)
            1 -> LoadingAnimations(isLoading) { isLoading = !isLoading }
            2 -> InteractiveAnimations()
        }
    }
}

@Composable
fun AnimatedHeader() {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .rotate(rotation),
                tint = Color.White
            )

            Text(
                text = "Animated UI",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .rotate(-rotation),
                tint = Color.White
            )
        }
    }
}

@Composable
fun AnimatedTabRow(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf("Cards", "Loading", "Interactive")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        tabs.forEachIndexed { index, title ->
            val isSelected = selectedTab == index
            val animatedColor by animateColorAsState(
                targetValue = if (isSelected) Color.White else Color.White.copy(alpha = 0.6f),
                animationSpec = tween(300)
            )
            val animatedScale by animateFloatAsState(
                targetValue = if (isSelected) 1.1f else 1f,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
            )

            Card(
                modifier = Modifier
                    .scale(animatedScale)
                    .clickable { onTabSelected(index) },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) Color.White.copy(alpha = 0.2f) else Color.Transparent
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = title,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    color = animatedColor,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun AnimatedCardList(showCards: Boolean) {
    val items = listOf(
        CardItem("Design", Icons.Default.Brush, Color(0xFFFF6B6B)),
        CardItem("Development", Icons.Default.Code, Color(0xFF4ECDC4)),
        CardItem("Marketing", Icons.Default.TrendingUp, Color(0xFF45B7D1)),
        CardItem("Analytics", Icons.Default.Analytics, Color(0xFF96CEB4)),
        CardItem("Support", Icons.Default.Support, Color(0xFFFECA57))
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items.size) { index ->
            AnimatedVisibility(
                visible = showCards,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                    )
                ) + fadeIn(
                    animationSpec = tween(500, delayMillis = index * 100)
                )
            ) {
                AnimatedCard(items[index])
            }
        }
    }
}

@Composable
fun AnimatedCard(item: CardItem) {
    var isExpanded by remember { mutableStateOf(false) }
    val animatedHeight by animateDpAsState(
        targetValue = if (isExpanded) 120.dp else 80.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(animatedHeight)
            .clickable { isExpanded = !isExpanded },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(item.color, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                AnimatedVisibility(
                    visible = isExpanded,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Text(
                        text = "This is an expanded view with more details about ${item.title}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingAnimations(
    isLoading: Boolean,
    onToggleLoading: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        // Pulsing Circle
        PulsingCircle(isLoading)

        // Rotating Loading
        RotatingLoader(isLoading)

        // Bouncing Dots
        BouncingDots(isLoading)

        Button(
            onClick = onToggleLoading,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text(
                text = if (isLoading) "Stop Loading" else "Start Loading",
                color = Color(0xFF667eea),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun PulsingCircle(isAnimating: Boolean) {
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .size(60.dp)
            .scale(if (isAnimating) scale else 1f)
            .background(Color.White, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = null,
            tint = Color(0xFFFF6B6B),
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
fun RotatingLoader(isAnimating: Boolean) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Icon(
        imageVector = Icons.Default.Sync,
        contentDescription = null,
        modifier = Modifier
            .size(48.dp)
            .rotate(if (isAnimating) rotation else 0f),
        tint = Color.White
    )
}

@Composable
fun BouncingDots(isAnimating: Boolean) {
    val infiniteTransition = rememberInfiniteTransition()

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(3) { index ->
            val offsetY by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = -20f,
                animationSpec = infiniteRepeatable(
                    animation = tween(600, delayMillis = index * 200, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            Box(
                modifier = Modifier
                    .size(12.dp)
                    .offset(y = if (isAnimating) offsetY.dp else 0.dp)
                    .background(Color.White, CircleShape)
            )
        }
    }
}

@Composable
fun InteractiveAnimations() {
    var heartCount by remember { mutableStateOf(0) }
    var showConfetti by remember { mutableStateOf(false) }

    LaunchedEffect(showConfetti) {
        if (showConfetti) {
            delay(2000)
            showConfetti = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        // Heart Button with Counter
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AnimatedCounter(heartCount)

                FloatingActionButton(
                    onClick = {
                        heartCount++
                        if (heartCount % 5 == 0) showConfetti = true
                    },
                    containerColor = Color(0xFFFF6B6B),
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Like",
                        tint = Color.White
                    )
                }
            }
        }

        // Confetti Animation
        AnimatedVisibility(
            visible = showConfetti,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut()
        ) {
            Text(
                text = "🎉 Milestone Reached! 🎉",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun AnimatedCounter(count: Int) {
    val animatedCount by animateIntAsState(
        targetValue = count,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    Text(
        text = animatedCount.toString(),
        style = MaterialTheme.typography.displayMedium,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF667eea)
    )
}

data class CardItem(
    val title: String,
    val icon: ImageVector,
    val color: Color
)