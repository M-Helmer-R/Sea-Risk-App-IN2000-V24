package no.uio.ifi.in2000.testgit.ui.home.dialog
import android.Manifest
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
                            HomeEvent.SetUserPosition(
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
//                    } ?: run {
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
                Text("Velg tillatelser")
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

@Composable
fun DeniedPermissionDialog(
    onEvent: (HomeEvent) -> Unit,
    context : Context = LocalContext.current
){
    AlertDialog(
        onDismissRequest = { },
        title = { Text( text = "Mangler posisjons-tilgang")},
        text = { Text(text = "Plask trenger tilgang til din posisjon for å vise de næmeste byene")},
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

@Composable
fun DisabledLocationDialog(
    onEvent: (HomeEvent) -> Unit,
    context : Context = LocalContext.current
){
    AlertDialog(
        onDismissRequest = { onEvent(HomeEvent.HideDeniedPermissionDialog)},
        title = { Text( text = "Lokasjon slått av")},
        text = { Text(
            text = "Gå til innstillinger og slå på lokasjon"
        )
        },
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

