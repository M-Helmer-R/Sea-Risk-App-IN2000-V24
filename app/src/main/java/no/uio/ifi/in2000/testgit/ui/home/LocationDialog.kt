@file:OptIn(ExperimentalPermissionsApi::class)

package no.uio.ifi.in2000.testgit.ui.home
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import no.uio.ifi.in2000.testgit.ui.theme.White

//Permission dialog. If user does not give permission, app defaults to Oslo.
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
    ) {
        permission ->
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
                    } ?: run {
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
                    } ?: run {
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
        title = { Text( text = "Tilgang til posisjon")},
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
                Text("Gi tillatelser")
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

//Dialog if user has denied permission on device. Show button to navigate to app settings

@Composable
fun DeniedPermissionDialog(
    onEvent: (HomeEvent) -> Unit,
    context : Context = LocalContext.current
){
    AlertDialog(
        onDismissRequest = { },
        title = { Text( text = "Mangler posisjons-tilgang")},
        text = { Text( text = "Plask trenger tilgang til din posisjon for å vise de næmeste byene")},
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


//Dialog if user has disabled location on device. Show button to navigate to device settings
@Composable
fun DisabledLocationDialog(
    onEvent: (HomeEvent) -> Unit,
    context : Context = LocalContext.current
){
    AlertDialog(
        onDismissRequest = { onEvent(HomeEvent.HideDeniedPermissionDialog)},
        title = { Text( text = "Lokasjon slått av")},
        text = { Text( text = "Gå til innstillinger og slå på lokasjon") },
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

//Shows permission status on HomeScreen
@SuppressLint("DefaultLocale")
@Composable
fun LocationStatus(
    locationState: MultiplePermissionsState,
    userLat : Double,
    userLon: Double
){
    if (locationState.allPermissionsGranted){
        Text(
            text = "Din posisjon: ${String.format("%.2f",userLat)}, ${String.format("%.2f",userLon)}",
            style = MaterialTheme.typography.bodySmall.copy(color = White),
        )
    } else {
        Row {
            Text(
                text = "Lokasjonsstilltelse: ",
                style = MaterialTheme.typography.bodySmall.copy(color = White),
            )
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription ="Location",
                tint = Color.Red
            )
        }
    }
}