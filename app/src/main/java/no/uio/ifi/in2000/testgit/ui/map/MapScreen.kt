package no.uio.ifi.in2000.testgit.ui.map


import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import no.uio.ifi.in2000.testgit.data.map.ReverseGeocodeCallback

import com.mapbox.api.geocoding.v5.GeocodingCriteria
import com.mapbox.api.geocoding.v5.MapboxGeocoding
import com.mapbox.api.geocoding.v5.models.GeocodingResponse
import no.uio.ifi.in2000.testgit.data.map.GeoCodeDataSource
import no.uio.ifi.in2000.testgit.data.map.GeoCodeRepository
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call

@OptIn(MapboxExperimental::class)
@Composable
fun Mapscreen(mapScreenViewModel: MapScreenViewModel = viewModel()){
    var lat: Double
    var lon: Double
    val locationUiState = mapScreenViewModel.locationUIState.collectAsState()
    val dialogUIState = mapScreenViewModel.dialogUIState.collectAsState()



    Box {
        if (dialogUIState.value.isVisible == true){

                AlertDialogExample(
                    onDismissRequest = {mapScreenViewModel.hideDialog() },
                    onConfirmation = { mapScreenViewModel.hideDialog() },
                    dialogTitle = locationUiState.value.placeName,
                    dialogText = "Vil du se mer info om ${locationUiState.value.placeName}?",
                    icon = Icons.Default.Info
                )

        }
        MapboxMap(

            Modifier.fillMaxSize(),
            mapViewportState = MapViewportState().apply {
                setCameraOptions {
                    zoom(3.7)
                    center(Point.fromLngLat(11.49537, 64.01487))
                    pitch(0.0)
                    bearing(0.0)
                }


            },
            onMapClickListener =  { point ->
                lat = point.latitude()
                lon = point.longitude()
                mapScreenViewModel.loadPlaceName(lon, lat)

                true
            },

            ) {

        }
    }
}



@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()

        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Ja")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Nei")
            }
        }
    )
}



