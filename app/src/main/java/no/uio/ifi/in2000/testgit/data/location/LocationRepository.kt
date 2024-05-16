package no.uio.ifi.in2000.testgit.data.location

import android.content.Context
import android.location.Location
import androidx.annotation.RequiresPermission
import android.Manifest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource


interface LocationRepository {
    fun getUserLocation(callback: (Location?) -> Unit)
}

class LocationRepositoryImpl(private val context: Context) : LocationRepository {
    @RequiresPermission(
        anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION]
    )
    override fun getUserLocation(callback: (Location?) -> Unit) {
        val locationClient = LocationServices.getFusedLocationProviderClient(context)
        locationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val location = task.result
                callback(location)
            } else {
                callback(null)
            }
        }
    }
}

