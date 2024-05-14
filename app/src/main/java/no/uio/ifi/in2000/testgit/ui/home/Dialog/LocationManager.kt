@file:OptIn(ExperimentalPermissionsApi::class, ExperimentalPermissionsApi::class,
    ExperimentalPermissionsApi::class
)

package no.uio.ifi.in2000.testgit.ui.home.Dialog

import android.Manifest
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import no.uio.ifi.in2000.testgit.ui.home.HomeEvent
import no.uio.ifi.in2000.testgit.ui.home.HomeUiState
import no.uio.ifi.in2000.testgit.ui.theme.White

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
            if (locationPermissionState.allPermissionsGranted) {
                Log.w("LOCATION_MANAGER:", "Permissions granted")
                getUserLocation(context) { location ->
                    Log.w("LOCATION_MANAGER:", "location: ${location.toString()}")
                    location?.let {
                        Log.w("LOCATION_MANAGER:", "getUserLocation: ${location.latitude} ${location.longitude}")
                        onEvent(
                            HomeEvent.setUserPosition(
                                lon = location.longitude,
                                lat = location.latitude
                            )
                        )
//                    } ?: run {
                        Log.w("LOCATION_MANAGER:", "getUserLocation failed")
                        onEvent(HomeEvent.showPermissionDialog)
                    }
                }
            } else {
                Log.w("LOCATION_MANAGER:", "Permissions not granted")
                onEvent(HomeEvent.showPermissionDialog)
            }
        }
    ) {
        Icon(imageVector = Icons.Outlined.Refresh, contentDescription = "Get location")
    }
}

@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,],
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
            //Log.w("LOCATION_MANAGER:", "location client: ${task.result.latitude} ${task.result.longitude}")
        } else {
            Log.w("LOCATION_MANAGER:", "location client failed")
            callback(null)
        }
    }
}
@Composable
fun LocationStatus(
    locationState: MultiplePermissionsState,
    homeUiState: HomeUiState
){
    Column {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "Delt posisjon: ",
                style = MaterialTheme.typography.bodySmall.copy(color = White),
                modifier = Modifier.background(Color.Transparent)
            )
            when (locationState.allPermissionsGranted) {
                true -> Icon(
                    Icons.Filled.CheckCircle,
                    "Location",
                    tint = Color.Green

                )
                false -> Icon(
                    Icons.Filled.Clear,
                    "Location",
                    tint = Color.Red
                )
            }
        }
        Text(
            text =
            if (locationState.allPermissionsGranted) {
                "Din posisjon: ${String.format("%.2f",homeUiState.userLat)}, ${String.format("%.2f",homeUiState.userLon)}"
            } else {
                "Din posisjon: - , -"
            },
            style = MaterialTheme.typography.bodySmall.copy(color = White)
        )
    }
}