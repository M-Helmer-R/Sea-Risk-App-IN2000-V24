package no.uio.ifi.in2000.testgit.data.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

//Interface for location repository
interface LocationRepository {
    fun getUserLocation(callback: (Location?) -> Unit)
}

//Implementation of location repository using Google Play Services Location API
class LocationRepositoryImpl(private val context: Context) : LocationRepository {
    //Get user location using Google Play Services Location API
    @RequiresPermission(
        anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION]
    )
    override fun getUserLocation(callback: (Location?) -> Unit) {
        val hasFineLocationPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val hasCoarseLocationPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        val priority = when {
            hasFineLocationPermission -> Priority.PRIORITY_HIGH_ACCURACY
            hasCoarseLocationPermission -> Priority.PRIORITY_BALANCED_POWER_ACCURACY
            else -> return callback(null)  // No permission granted, handle appropriately
        }
        val locationClient = LocationServices.getFusedLocationProviderClient(context)
        locationClient.getCurrentLocation(
            priority,
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

