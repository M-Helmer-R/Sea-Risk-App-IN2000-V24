@file:OptIn(ExperimentalPermissionsApi::class)

package no.uio.ifi.in2000.testgit.ui.home.Dialog

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import no.uio.ifi.in2000.testgit.ui.home.HomeEvent
import no.uio.ifi.in2000.testgit.ui.home.HomeViewModel

/*
@Composable
fun LocationPermissions(
    text: String,
    rationale : String,
    locationState: PermissionState
){
    LocationPermissions(
        text = text,
        rationale = rationale,
        locationState = rememberMultiplePermissionsState(
            permissions = listOf(
                locationState.permission
            )
        )
    )
}
*/
@Composable
fun PermissionDialog(
    onEvent : (HomeEvent) -> Unit,
    homeViewModel : HomeViewModel,
) {
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
        onDismissRequest = { onEvent(HomeEvent.hidePermissionDialog) },
        confirmButton = {
                Button(
                    onClick = {
                        locationPermissionResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }
                ) {
                    Text(text = "Get")
                }
        }
    )

}
