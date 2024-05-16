@file:OptIn(ExperimentalPermissionsApi::class, ExperimentalPermissionsApi::class,
    ExperimentalPermissionsApi::class
)

package no.uio.ifi.in2000.testgit.ui.home
import android.Manifest
import android.app.Activity
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
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

//Permission dialog. If user does not give permission, app defaults to Oslo.
@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)
@Composable
fun PermissionRationaleDialog(
    onEvent: (HomeEvent) -> Unit,
    context : Context = LocalContext.current,
    locationPermissionState: MultiplePermissionsState,
    locationViewModel: LocationViewModel
) {
    val locationPermissionResultLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (locationPermissionState.allPermissionsGranted) {
            locationViewModel.location.observe(context as LifecycleOwner, Observer { location ->
                location?.let {
                    onEvent(HomeEvent.SetUserPosition(lon = it.longitude, lat = it.latitude))
                } ?: run {
                    onEvent(HomeEvent.ShowDisabledLocationDialog)
                }
            })
            locationViewModel.fetchLocation()
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            onEvent(HomeEvent.ShowPermissionDialog)
        } else {
            onEvent(HomeEvent.ShowDeniedPermissionDialog)
        }
    }
    AlertDialog(
        onDismissRequest = { onEvent(HomeEvent.HidePermissionDialog)},
        title = { Text( text = "Tillatelse til posisjon")},
        text = { Text(
            text = "Plask trenger din poisjon. " +
                    "For best brukeropplevelse gi Plask din tillatelse for å hente din posisjon")
               },
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
                Text("Vis tillatelser")
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
        onDismissRequest = { onEvent(HomeEvent.HideDeniedPermissionDialog) },
        title = { Text( text = "Tillatelse avvist")},
        text = { Text( text = "For den best brukeropplevelse anbefaler vi å slå på precise location ")
               },
        confirmButton = {
            Button(
                onClick = {
                    onEvent(HomeEvent.HideDeniedPermissionDialog)
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.fromParts("package", context.packageName, null)
                    context.startActivity(intent)
                },
            ) {
                Text("Gå til innstillinger")
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


//Dialog if user has disabled location on device. Show button to navigate to device settings
@Composable
fun DisabledLocationDialog(
    onEvent: (HomeEvent) -> Unit,
    context : Context = LocalContext.current
){
    AlertDialog(
        onDismissRequest = { onEvent(HomeEvent.HideDeniedPermissionDialog)},
        title = { Text( text = "Posisjon slått av")},
        text = { Text( text = "Din posisjon er slått av på enheten din. " +
                "For best brukeropplevelse slå på posisjon.")
        },
        confirmButton = {
            Button(
                onClick = {
                    onEvent(HomeEvent.HideDisabledLocationDialog)
                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) },
                ) {
                Text("Gå til innstillinger")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onEvent(HomeEvent.HideDisabledLocationDialog)
                }
            ) {
                Text("Avvis")
            }
        }
    )
}