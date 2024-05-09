package no.uio.ifi.in2000.testgit.ui.home.Dialog

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import no.uio.ifi.in2000.testgit.ui.home.HomeEvent
import no.uio.ifi.in2000.testgit.ui.home.HomeViewModel

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

@Composable
fun LocationDialog(
    onEvent: (HomeEvent) -> Unit,
    homeViewModel : HomeViewModel,
){

         val locationPermissionResultLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = {isGranted ->
                homeViewModel.onPermissionResult(
                    permission = Manifest.permission.ACCESS_FINE_LOCATION,
                    isGranted = true,
                )
            }
        )

    AlertDialog(
        onDismissRequest = {
            onEvent(HomeEvent.hideLocationDialog)
        },
        icon = {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Location Icon"
            )
        },
        title = {
            Text( text = "Location permission")
        },

        confirmButton = {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Button(
                    onClick = {
                        onEvent(HomeEvent.hideLocationDialog)
                        onEvent(HomeEvent.showManualLocationDialog)
                    }
                )
                {
                    Text(text = "Set manually")
                }
                Button(
                    onClick = {
                        onEvent(HomeEvent.hideLocationDialog)
                        onEvent(HomeEvent.showPermissionDialog)
                    })
                {
                    Text(text = "Share location")
                }
            }
        },
        /*
                dismissButton = {
                    Button(
                        onClick = {
                            onEvent(HomeEvent.hidePermissionDialog)
                            onEvent(HomeEvent.showDeniedPermissionDialog)
                        }
                    )
                    {
                        Text(text = "Deny")
                    }
                }
         */
    )

}

