@file:OptIn(ExperimentalPermissionsApi::class, ExperimentalPermissionsApi::class,
    ExperimentalPermissionsApi::class, ExperimentalPermissionsApi::class
)

package no.uio.ifi.in2000.testgit.ui.dialog
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
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