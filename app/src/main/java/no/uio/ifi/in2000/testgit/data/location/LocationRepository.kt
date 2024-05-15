package no.uio.ifi.in2000.testgit.data.location

/*
import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import no.uio.ifi.in2000.testgit.ui.home.HomeEvent
import no.uio.ifi.in2000.testgit.ui.home.dialog.getUserLocation

class LocationRepository (
    context: Context
){
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    init {
        setupLocation()
    }

    private fun setupLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(conte)

        locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000 // 10 seconds
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                // Handle location updates
            }
        }
    }

    fun startLocationUpdates() {
        if (isLocationPermissionGranted()) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
        } else {
            // Handle permission not granted
        }
    }

    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun isLocationPermissionGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Other methods related to
}

@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)
@Composable
fun PermissionRationaleDialog(
    onEvent: (HomeEvent) -> Unit,
    context : Context = LocalContext.current

) {

    val locationPermissionResultLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {permission ->
        when {
            permission.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                Log.w("PermissionRationaleDialog", "Fine location granted")
                getUserLocation(context) { location ->
                    Log.w("LOCATION_MANAGER:", "location: ${location.toString()}")
                    location?.let {
                        Log.w("LOCATION_MANAGER:", "getUserLocation: ${location.latitude} ${location.longitude}")
                        onEvent(
                            HomeEvent.SetUserPosition(
                                lon = location.longitude,
                                lat = location.latitude
                            )
                        )
//                    } ?: run {
                        Log.w("LOCATION_MANAGER:", "getUserLocation failed")
                    }
                }
            }

            permission.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                Log.w("PermissionRationaleDialog", "Coarse location granted")
                getUserLocation(context) { location ->
                    Log.w("LOCATION_MANAGER:", "location: ${location.toString()}")
                    location?.let {
                        Log.w("LOCATION_MANAGER:", "getUserLocation: ${location.latitude} ${location.longitude}")
                        onEvent(
                            HomeEvent.SetUserPosition(
                                lon = location.longitude,
                                lat = location.latitude
                            )
                        )
//                    } ?: run {
                        Log.w("LOCATION_MANAGER:", "getUserLocation failed")
                    }
                }

            }

            else -> {
                Log.w("PermissionRationaleDialog", "No permissions granted")
                onEvent(HomeEvent.HidePermissionDialog)

            }
        }
    }

    AlertDialog(
        onDismissRequest = { onEvent(HomeEvent.HidePermissionDialog)},
        title = { Text( text = "Tilgang til posisjon") },
        text = { Text(text = "Plask trenger tilgang til din posisjon for å vise de næmeste byene") },
        confirmButton = {
            Button(
                onClick = {
                    Log.w("PermissionRationaleDialog", "Requesting permissions")
                    onEvent(HomeEvent.HidePermissionDialog)
                    locationPermissionResultLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                },
            ) {
                Text("Velg tillatelser")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onEvent(HomeEvent.HidePermissionDialog)
                }
            ) {
                Text("Avvis")
            }
        }
    )
}

@Composable
fun DeniedPermissionDialog(
    onEvent: (HomeEvent) -> Unit,
    context : Context = LocalContext.current
){
    AlertDialog(
        onDismissRequest = { },
        title = { Text( text = "Mangler posisjons-tilgang") },
        text = { Text(text = "Plask trenger tilgang til din posisjon for å vise de næmeste byene") },
        confirmButton = {
            TextButton(
                onClick = {
                    onEvent(HomeEvent.HideDeniedPermissionDialog)
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.fromParts("package", context.packageName, null)
                    context.startActivity(intent)
                },
            ) {
                Text("Gå til applikasjonsinnstillinger")
            }
        },
    )
}

@Composable
fun DisabledLocationDialog(
    onEvent: (HomeEvent) -> Unit,
    context : Context = LocalContext.current
){
    AlertDialog(
        onDismissRequest = { onEvent(HomeEvent.HideDeniedPermissionDialog)},
        title = { Text( text = "Lokasjon slått av") },
        text = { Text(
            text = "Gå til innstillinger og slå på lokasjon"
        )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onEvent(HomeEvent.HideDisabledLocationDialog)
                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) },
            ) {
                Text("Gå til innstillinger")
            }
        }
    )
}



@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)
@Composable
fun GetLocation(
    locationPermissionState: MultiplePermissionsState,
    context: Context,
    onEvent: (HomeEvent) -> Unit
){
    if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.ACCESS_FINE_LOCATION)){
        Log.w("LOCATION_MANAGER:", "Permissions not granted")
        onEvent(HomeEvent.ShowPermissionDialog)
    }
    else {
        if (locationPermissionState.allPermissionsGranted) {
            Log.w("LOCATION_MANAGER", "Permissions granted")
            getUserLocation(context) { location ->
                Log.w("LOCATION_MANAGER:", "location: ${location.toString()}")
                location?.let {
                    Log.w("LOCATION_MANAGER:", "getUserLocation: ${location.latitude} ${location.longitude}")
                    onEvent(
                        HomeEvent.SetUserPosition(
                            lon = location.longitude,
                            lat = location.latitude
                        )
                    )
                } ?: run {
                    Log.w("LOCATION_MANAGER:", "getUserLocation failed")
                    onEvent(HomeEvent.ShowDisabledLocationDialog)
                }
            }
        } else {
            onEvent(HomeEvent.ShowDeniedPermissionDialog)
        }
    }
}

@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)
@Composable
fun LocationButton(
    locationPermissionState: MultiplePermissionsState,
    context: Context,
    onEvent: (HomeEvent) -> Unit
) {
    Button(
        onClick = {
            Log.w("LOCATION_MANAGER:", "Button pressed")
            if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.ACCESS_FINE_LOCATION)){
                Log.w("LOCATION_MANAGER:", "Permissions not granted")
                onEvent(HomeEvent.ShowPermissionDialog)
            }
            else {
                if (locationPermissionState.allPermissionsGranted) {
                    Log.w("LOCATION_MANAGER", "Permissions granted")
                    getUserLocation(context) { location ->
                        Log.w("LOCATION_MANAGER:", "location: ${location.toString()}")
                        location?.let {
                            Log.w("LOCATION_MANAGER:", "getUserLocation: ${location.latitude} ${location.longitude}")
                            onEvent(
                                HomeEvent.SetUserPosition(
                                    lon = location.longitude,
                                    lat = location.latitude
                                )
                            )
                        } ?: run {
                            Log.w("LOCATION_MANAGER:", "getUserLocation failed")
                            onEvent(HomeEvent.ShowDisabledLocationDialog)
                        }
                    }
                } else {
                    onEvent(HomeEvent.ShowDeniedPermissionDialog)
                }
            }
        }
    ) {
        Icon(imageVector = Icons.Outlined.Refresh, contentDescription = "Get location")
    }
}

@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION],
)
fun getUserLocation(
    context: Context,
    callback: (Location?) -> Unit
) {
    val locationClient = LocationServices.getFusedLocationProviderClient(context)
    locationClient.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        CancellationTokenSource().token
    ).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Log.w("LOCATION_MANAGER:", "location task sucsesfull")
            val location = task.result
            callback(location)
        } else {
            Log.w("LOCATION_MANAGER:", "location client failed")
            callback(null)
        }
    }
}
@SuppressLint("DefaultLocale")
@Composable
fun LocationStatus(
    locationState: MultiplePermissionsState,
    homeUiState: HomeUiState
){
    if (locationState.allPermissionsGranted){
        Text(
            text = "Din posisjon: ${String.format("%.2f",homeUiState.userLat)}, ${String.format("%.2f",homeUiState.userLon)}",
            style = MaterialTheme.typography.bodySmall.copy(color = White),
        )
    } else {
        Row {
            Text(text = "Lokasjonsstilltelse: ",
                style = MaterialTheme.typography.bodySmall.copy(color = White),
            )
            Icon(imageVector = Icons.Filled.Clear,
                contentDescription ="Location",
                tint = Color.Red
            )
        }
    }
}

 */