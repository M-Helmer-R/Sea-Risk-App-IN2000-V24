@file:OptIn(ExperimentalPermissionsApi::class)

package no.uio.ifi.in2000.testgit.ui.home.Dialog

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import no.uio.ifi.in2000.testgit.ui.home.HomeEvent

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
                            HomeEvent.setUserPosition(
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
            }

            else -> {
                Log.w("PermissionRationaleDialog", "No permissions granted")
                onEvent(HomeEvent.hidePermissionDialog)
                onEvent(HomeEvent.showDeniedPermissionDialog)
            }
        }
    }

    AlertDialog(
        onDismissRequest = { onEvent(HomeEvent.hidePermissionDialog)},
        title = { Text( text = "Tilgang til posisjon")},
        text = {
            Column (
                modifier = Modifier.padding(4.dp)
            ){
                Text(
                    text = "For å vise de næmeste byene trenger Plask tilgang til din posisjon.\n" +
                            "Hvis du ikke ønsker dette kan du sette posisjon manuelt"
                )
                Button(
                    onClick = {
                        onEvent(HomeEvent.hidePermissionDialog)
                        onEvent(HomeEvent.showManualLocationDialog)
                    }
                ) {
                    Text(text = "Set posisjon manuelt")
                }
            }


        },
        confirmButton = {
            Button(
                onClick = {
                    locationPermissionResultLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                    onEvent(HomeEvent.hidePermissionDialog)
                },
            ) {
                Text("Velg tillatelser")
            }
        },

        dismissButton = {
            TextButton(
                onClick = {
                    onEvent(HomeEvent.hidePermissionDialog)
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
        onDismissRequest = { onEvent(HomeEvent.hideDeniedPermissionDialog)},
        title = { Text( text = "Tilgang til posisjon")},
        text = { Text(
                    text = "F"
                )
        },
        confirmButton = {
            Button(
                onClick = {
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
                    onEvent(HomeEvent.hideDeniedPermissionDialog)
                }
            ) {
                Text("Avvis")
            }
        }
    )
}


@Composable
fun ManualLocationDialog(
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
){

    var lat by remember { mutableStateOf("") }
    var lon by remember { mutableStateOf("") }

    val errorMessage = "Not valid input"
    var latError by rememberSaveable { mutableStateOf(false) }
    var lonError by rememberSaveable { mutableStateOf(false) }

    fun validateInput(lat : String, lon : String) {
        Log.w("ADD_CITY_DIALOG", "Validating input")
        latError = validateCoordinates(lat)
        lonError = validateCoordinates(lon)
    }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(HomeEvent.hideManualLocationDialog)
        },

        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Button(
                    onClick = {
                        onEvent(HomeEvent.hideManualLocationDialog)
                    }
                ) {
                    Text(text = "Avbryt")
                }

                Button(
                    onClick = {
                        validateInput(lat, lon)
                        if ( !latError && !lonError){
                            onEvent(HomeEvent.setUserPosition(lon.toDouble(), lat.toDouble()))
                            onEvent(HomeEvent.updateNearest)
                        }
                    }
                ) {
                    Text(text = "Set position")
                }

            }
        },
        title = { Text(text = "Set position manually")},
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                DoubleTextField(
                    title = "Latitude",
                    placeholder = "Enter the latitude",
                    text = lat,
                    onValueChange = {lat = it},
                    error = latError,
                    errorMessage = errorMessage

                )
                DoubleTextField(
                    title = "Longitude",
                    placeholder = "Enter the longitude",
                    text = lon,
                    onValueChange = {lon = it},
                    error = lonError,
                    errorMessage = errorMessage
                )
            }
        }
    )
}
