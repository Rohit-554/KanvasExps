package io.jadu.kanvasexp.burpy

import android.app.Activity
import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material.icons.twotone.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.ActiveCaloriesBurnedRecord
import androidx.health.connect.client.records.BasalBodyTemperatureRecord
import androidx.health.connect.client.records.BasalMetabolicRateRecord
import androidx.health.connect.client.records.BloodGlucoseRecord
import androidx.health.connect.client.records.BloodPressureRecord
import androidx.health.connect.client.records.BodyFatRecord
import androidx.health.connect.client.records.BodyTemperatureRecord
import androidx.health.connect.client.records.BodyWaterMassRecord
import androidx.health.connect.client.records.BoneMassRecord
import androidx.health.connect.client.records.CervicalMucusRecord
import androidx.health.connect.client.records.CyclingPedalingCadenceRecord
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.records.ElevationGainedRecord
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.health.connect.client.records.FloorsClimbedRecord
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.HeartRateVariabilityRmssdRecord
import androidx.health.connect.client.records.HeightRecord
import androidx.health.connect.client.records.HydrationRecord
import androidx.health.connect.client.records.IntermenstrualBleedingRecord
import androidx.health.connect.client.records.LeanBodyMassRecord
import androidx.health.connect.client.records.MenstruationFlowRecord
import androidx.health.connect.client.records.NutritionRecord
import androidx.health.connect.client.records.OvulationTestRecord
import androidx.health.connect.client.records.OxygenSaturationRecord
import androidx.health.connect.client.records.PowerRecord
import androidx.health.connect.client.records.RespiratoryRateRecord
import androidx.health.connect.client.records.RestingHeartRateRecord
import androidx.health.connect.client.records.SexualActivityRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.SpeedRecord
import androidx.health.connect.client.records.StepsCadenceRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.records.Vo2MaxRecord
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.records.WheelchairPushesRecord
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

val permissions = setOf(
    HealthPermission.getReadPermission(BasalMetabolicRateRecord::class),
    HealthPermission.getReadPermission(BloodGlucoseRecord::class),
    HealthPermission.getReadPermission(BloodPressureRecord::class),
    HealthPermission.getReadPermission(BodyFatRecord::class),
    HealthPermission.getReadPermission(BodyTemperatureRecord::class),
    HealthPermission.getReadPermission(ActiveCaloriesBurnedRecord::class),
    HealthPermission.getReadPermission(DistanceRecord::class),
    HealthPermission.getReadPermission(ExerciseSessionRecord::class),
    HealthPermission.getReadPermission(HeartRateRecord::class),
    HealthPermission.getReadPermission(HeartRateVariabilityRmssdRecord::class),
    HealthPermission.getReadPermission(HeightRecord::class),
    HealthPermission.getReadPermission(HydrationRecord::class),
    HealthPermission.getReadPermission(NutritionRecord::class),
    HealthPermission.getReadPermission(OxygenSaturationRecord::class),
    HealthPermission.getReadPermission(PowerRecord::class),
    HealthPermission.getReadPermission(RestingHeartRateRecord::class),
    HealthPermission.getReadPermission(RespiratoryRateRecord::class),
    HealthPermission.getReadPermission(SleepSessionRecord::class),
    HealthPermission.getReadPermission(StepsRecord::class),
    HealthPermission.getReadPermission(TotalCaloriesBurnedRecord::class),
    HealthPermission.getReadPermission(Vo2MaxRecord::class),
    HealthPermission.getReadPermission(WeightRecord::class),
    HealthPermission.getReadPermission(WheelchairPushesRecord::class),
    HealthPermission.getReadPermission(SexualActivityRecord::class),
    HealthPermission.getReadPermission(StepsCadenceRecord::class),
    HealthPermission.getReadPermission(IntermenstrualBleedingRecord::class),
    HealthPermission.getReadPermission(LeanBodyMassRecord::class),
    HealthPermission.getReadPermission(MenstruationFlowRecord::class),
    HealthPermission.getReadPermission(OvulationTestRecord::class),
    HealthPermission.getReadPermission(BasalBodyTemperatureRecord::class),
    HealthPermission.getReadPermission(BodyWaterMassRecord::class),
    HealthPermission.getReadPermission(BoneMassRecord::class),
    HealthPermission.getReadPermission(CervicalMucusRecord::class),
    HealthPermission.getReadPermission(CyclingPedalingCadenceRecord::class),
    HealthPermission.getReadPermission(ElevationGainedRecord::class),
    HealthPermission.getReadPermission(FloorsClimbedRecord::class),
    HealthPermission.getReadPermission(SpeedRecord::class),
)

@Composable
fun HealthConnectPermissionHandler(
    onPermissionsResult: (Boolean) -> Unit = {}
) {
    val context = LocalContext.current
    val activity = context as Activity
    val healthConnectClient = remember { HealthConnectClient.getOrCreate(context) }

    // Define required permissions
    val PERMISSIONS = remember {
        permissions
    }

    var showRationale by remember { mutableStateOf(false) }
    var permissionRequested by remember { mutableStateOf(false) }
    var healthConnectAvailable by remember { mutableStateOf(true) }

    // Permission launcher
    val requestPermissions = rememberLauncherForActivityResult(
        contract = PermissionController.createRequestPermissionResultContract()
    ) { granted ->
        permissionRequested = true
        if (granted.containsAll(PERMISSIONS)) {
            onPermissionsResult(true)
        } else {
            // Check if we should show rationale
            val shouldShowRationale = PERMISSIONS.any {
                ActivityCompat.shouldShowRequestPermissionRationale(activity, it)
            }
            showRationale = shouldShowRationale
            if (!shouldShowRationale) {
                onPermissionsResult(false)
            }
        }
    }

    // Initial check
    LaunchedEffect(Unit) {
        try {
            // Check Health Connect availability
            when (HealthConnectClient.getSdkStatus(context)) {
                HealthConnectClient.SDK_UNAVAILABLE -> {
                    healthConnectAvailable = false
                    onPermissionsResult(false)
                    return@LaunchedEffect
                }
                HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED -> {
                    // Prompt user to update Health Connect
                    try {
                        context.startActivity(
                            Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse("market://details?id=com.google.android.apps.healthdata")
                                setPackage("com.android.vending")
                            }
                        )
                    } catch (e: ActivityNotFoundException) {
                        context.startActivity(
                            Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.healthdata")
                            }
                        )
                    }
                    return@LaunchedEffect
                }
                else -> {}
            }

            // Check existing permissions
            val granted = healthConnectClient.permissionController.getGrantedPermissions()
            if (granted.containsAll(PERMISSIONS)) {
                onPermissionsResult(true)
            } else if (!permissionRequested) {
                // Check if we should show rationale first
                Log.d("HealthConnect", "Permissions not granted: $granted")
                requestPermissions.launch(PERMISSIONS)

                val shouldShowRationale = PERMISSIONS.any {
                    ActivityCompat.shouldShowRequestPermissionRationale(activity, it)
                }

                if (shouldShowRationale) {
                    showRationale = true
                } else {
                    requestPermissions.launch(PERMISSIONS)
                }
            }
        } catch (e: Exception) {
            Log.e("HealthConnect", "Error checking permissions", e)
            onPermissionsResult(false)
        }
    }

    // Rationale Dialog
    if (showRationale) {
        AlertDialog(
            onDismissRequest = {
                showRationale = false
                onPermissionsResult(false)
            },
            title = { Text("Health Data Access Required") },
            text = { Text("To provide this feature, we need access to your health data. Your data will only be used for this purpose and will not be shared.") },
            confirmButton = {
                Button(
                    onClick = {
                        showRationale = false
                        requestPermissions.launch(PERMISSIONS)
                    }
                ) {
                    Text("Grant Access")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showRationale = false
                        onPermissionsResult(false)
                    }
                ) {
                    Text("Deny")
                }
            }
        )
    }

    // Health Connect not available dialog
    if (!healthConnectAvailable) {
        AlertDialog(
            onDismissRequest = { onPermissionsResult(false) },
            title = { Text("Health Connect Not Available") },
            text = { Text("This feature requires Health Connect, which is not available on your device.") },
            confirmButton = {
                Button(
                    onClick = { onPermissionsResult(false) }
                ) {
                    Text("OK")
                }
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthConnectScreen() {
    //  val context = LocalContext.current as Activity
    val viewModel: HealthConnectVM = viewModel()

    val shouldCheckForPermission = remember {
        mutableStateOf(false)
    }
    val isAllPermissionGranted = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val getAllRecords = remember { mutableStateOf<List<String>>(emptyList()) }

    val providerPackageName = "com.google.android.apps.healthdata"
    /* HealthConnect().FetchRecords {  records ->
         getAllRecords.value = records
     }*/

    val availabilityStatus = HealthConnectClient.getSdkStatus(context)
    if (availabilityStatus == HealthConnectClient.SDK_UNAVAILABLE) {
        Log.d("HealthConnect", "SDK unavailable")
        return // early return as there is no viable integration
    }
    if (availabilityStatus == HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED) {
        // Optionally redirect to package installer to find a provider, for example:
        val uriString =
            "market://details?id=$providerPackageName&url=healthconnect%3A%2F%2Fonboarding"
        context.startActivity(
            Intent(Intent.ACTION_VIEW).apply {
                setPackage("com.android.vending")
                data = Uri.parse(uriString)
                putExtra("overlay", true)
                putExtra("callerId", context.packageName)
            }
        )
        return
    }
    val healthConnectClient = HealthConnectClient.getOrCreate(context)
// Issue operations with healthConnectClient

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = MaterialTheme.colorScheme.secondary
                ),
                title = {
                    Text("Allow Health Connect", style = MaterialTheme.typography.displayMedium)
                },
                navigationIcon = {
                    IconButton(onClick = { /*navController.popBackStack() */ }) {
                        Icon(
                            Icons.AutoMirrored.Default.KeyboardArrowRight,
                            "Back",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            )
        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                //HeaderSection(modifier = Modifier.padding(horizontal = 48.dp))

                Spacer(modifier = Modifier.height(24.dp))

                // Sync with Health Connect text

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = "Sync with Health Connect",
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "p.",
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = "Permissions Required",
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        PermissionList()
                    }
                }

                Spacer(modifier = Modifier.height(128.dp))
            }
            Button(
                onClick = {
                    if (!viewModel.hasAllPermissions) shouldCheckForPermission.value = true
                },
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .height(72.dp)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .padding(horizontal = 16.dp),
            ) {
                Text(
                    text = if (isAllPermissionGranted.value) "Connected" else "Set up Health Connect",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge,
                )
            }
        }
    }

    if (shouldCheckForPermission.value) {
        HealthConnectPermissionHandler()
    }

}

@Composable
fun PermissionList() {
    Column {
        PermissionItem(icon = Icons.TwoTone.Favorite, label = "Active calories burned")
        PermissionItem(icon = Icons.TwoTone.Favorite, label = "Basal metabolic rate")
        PermissionItem(icon = Icons.TwoTone.Favorite, label = "Distance")
        PermissionItem(icon = Icons.TwoTone.Favorite, label = "Exercise")
        PermissionItem(icon = Icons.TwoTone.Favorite, label = "Sleep")
        PermissionItem(icon = Icons.TwoTone.Favorite, label = "Steps")
        PermissionItem(icon = Icons.TwoTone.Favorite, label = "Total calories burned")
        PermissionItem(icon = Icons.TwoTone.Favorite, label = "Heart Rate")
        PermissionItem(icon = Icons.TwoTone.FavoriteBorder, label = "Resting Heart Rate")
        PermissionItem(icon = Icons.TwoTone.Favorite, label = "Respiratory Rate")
        PermissionItem(icon = Icons.TwoTone.Favorite, label = "Hydration")
    }
}

@Composable
fun PermissionItem(icon: ImageVector, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            fontSize = 16.sp,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface

        )
    }
}


