package io.jadu.kanvasexp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.skydoves.flexible.bottomsheet.material3.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetSize
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState


@Composable
fun bottomSheet() {
    var showPlanSaveSheet by remember { mutableStateOf(false) }

    val showPlanSheetState = rememberFlexibleBottomSheetState(
        isModal = true,
        skipSlightlyExpanded = true,
        skipIntermediatelyExpanded = true,
        allowNestedScroll = false,
        flexibleSheetSize = FlexibleSheetSize(fullyExpanded = 0.75f)
    )

    FlexibleBottomSheet(
        windowInsets = WindowInsets(0.dp),
        onDismissRequest = {
            showPlanSaveSheet = false
        },
        sheetState = showPlanSheetState,
        containerColor = Color.White,
        contentColor = MaterialTheme.colorScheme.secondary
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    "Save & Activate Meal Plan",
                    style = BodyXLarge(),
                    fontWeight = FontWeight.SemiBold
                )
                VSpacer(32.dp)
                Text(
                    "Enter Plan Name",
                    style = BodyNormal(),
                )
                VSpacer(8.dp)
                OutlinedTextField(
                    value = mealPlannerVM.mealPlanTitle,
                    onValueChange = { mealPlannerVM.mealPlanTitle = it },
                    shape = SquircleShape(24f),
                    modifier = Modifier
                        .fillMaxWidth().height(56.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.3f
                        ),
                        unfocusedContainerColor = MaterialTheme.colorScheme.secondary.copy(
                            alpha = 0.3f
                        ),
                        unfocusedBorderColor = Color.Transparent,
                        unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
                    ),
                    placeholder = {
                        Text(
                            text = "Enter a Plan Name",
                            style = BodyNormal(),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 18.sp,
                        letterSpacing = 0.05.em,
                        lineHeight = 24.sp,
                        fontFamily = QuickSandsFamily()
                    ),
                    visualTransformation = VisualTransformation.None,
                    leadingIcon = {
                        Icon(
                            Icons.Rounded.RestaurantMenu,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    },
                    enabled = true
                )
                VSpacer(24.dp)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "Start Date", style = BodyLarge())
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        Modifier
                            .clip(SquircleShape(36f))
                            .clickable { openCalendarDialog.value = true }
                            .background(
                                MaterialTheme.colorScheme.secondary.copy(
                                    alpha = 0.3f
                                ), SquircleShape(36f)
                            )
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Icon(
                            Icons.Outlined.CalendarMonth,
                            contentDescription = "Calendar"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = when {
                                mealPlannerVM.mealPlanStartDate.isToday() -> "Today"
                                mealPlannerVM.mealPlanStartDate.isYesterday() -> "Yesterday"
                                mealPlannerVM.mealPlanStartDate.isTomorrow() -> "Tomorrow"
                                else -> formatDate(mealPlannerVM.mealPlanStartDate)
                            },
                            style = BodyXLarge().copy(fontWeight = FontWeight.SemiBold),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            imageVector = Icons.Rounded.ArrowDropDown,
                            contentDescription = "Select Unit",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }

                }
                VSpacer(24.dp)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Generate Shopping List\nfor the Plan?",
                        style = BodyLarge()
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(
                        checked = mealPlannerVM.generateShoppingList,
                        onCheckedChange = { mealPlannerVM.generateShoppingList = it })
                }
            }
            Button(
                onClick = {
                    mealPlannerVM.saveMealPlan(navController)
                },
                shape = SquircleShape(24f),
                modifier = Modifier
                    .padding(WindowInsets.navigationBars.asPaddingValues())
                    .padding(16.dp)
                    .height(54.dp)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                Text(text = "Save", color = Color.White, style = BodyLarge())
            }
        }
    }
}



