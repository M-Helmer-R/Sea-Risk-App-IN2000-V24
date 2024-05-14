@file:OptIn(ExperimentalPermissionsApi::class, ExperimentalPermissionsApi::class)

package no.uio.ifi.in2000.testgit.ui.home.Dialog

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import no.uio.ifi.in2000.testgit.ui.home.HomeEvent

@Composable
fun PermissionRequestButton(
    locationState: MultiplePermissionsState,
    onEvent: (HomeEvent) -> Unit,
    ) {
    if (locationState.allPermissionsGranted) {
        Row(
            modifier = Modifier,
           verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Outlined.CheckCircle,
                "Location", modifier = Modifier.size(48.dp))
            Text(text = "Location",
                modifier = Modifier.background(Color.Transparent)
            )
        }
    } else {
        Button(onClick = {
            locationState.launchMultiplePermissionRequest()
            onEvent(HomeEvent.showPermissionDialog)
        }){
            Text("Request Location")
        }
    }
}

@Composable
fun PermissionRationaleDialog(
    locationState : MultiplePermissionsState,
    onEvent: (HomeEvent) -> Unit
) {
    val locationPermissionResultLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {permission ->
        when {
            permission.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {

            }
            permission.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {

            } else -> {

        }
        }
    }
    AlertDialog(
        onDismissRequest = { onEvent(HomeEvent.hidePermissionDialog)},
        title = { Text( text = "Location")},
        text = { Text(text = "forklaring")},
        confirmButton = {
            TextButton(
                onClick = {
                    onEvent(HomeEvent.hidePermissionDialog)
                    locationPermissionResultLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
            ) {
                Text("Continue")
            }
                        },

        dismissButton = {
            TextButton(
                onClick = {
                    onEvent(HomeEvent.hidePermissionDialog)
                    onEvent(HomeEvent.showManualLocationDialog)
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}
