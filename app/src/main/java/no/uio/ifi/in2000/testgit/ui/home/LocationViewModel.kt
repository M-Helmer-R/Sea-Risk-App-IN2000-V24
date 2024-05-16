@file:OptIn(ExperimentalPermissionsApi::class, ExperimentalPermissionsApi::class,
    ExperimentalPermissionsApi::class, ExperimentalPermissionsApi::class
)

package no.uio.ifi.in2000.testgit.ui.home
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import no.uio.ifi.in2000.testgit.ui.theme.White

// Gets user location

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
