package no.uio.ifi.in2000.testgit.ui.map

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
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

import no.uio.ifi.in2000.testgit.data.map.GeocodingPlacesResponse

data class SearchUIState(var geocodingPlacesResponse: GeocodingPlacesResponse?)

@Composable
fun SearchBar(searchUIState: SearchUIState, mapScreenViewModel: MapScreenViewModel){
    var text by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(){
        TextField(
            value = text,
            onValueChange = {
                text = it
                if (text.isNotEmpty()) {
                    expanded = true
                    mapScreenViewModel.loadSearchUIState(text)
                }

                else {
                    expanded = false
                    mapScreenViewModel.unloadSearchUIState()
                }
                            },
            label = {Text("Søk etter sted")}
        )

        if (expanded && searchUIState.geocodingPlacesResponse != null){
            LazyColumn {
                //To be replaced with clickable card which navigates
                items(searchUIState.geocodingPlacesResponse!!.features){
                    Text(it.properties.name)
                }
            }
        }

    }

}

@OptIn(MapboxExperimental::class)
@Composable
fun Mapscreen(mapScreenViewModel: MapScreenViewModel = viewModel()){
    var lat: Double
    var lon: Double
    val locationUiState = mapScreenViewModel.locationUIState.collectAsState()
    val dialogUIState = mapScreenViewModel.dialogUIState.collectAsState()
    val searchUIState = mapScreenViewModel.searchUIState.collectAsState()
    val oceanForeCastUIState = mapScreenViewModel.oceanForeCastUIState.collectAsState()


    Column(modifier = Modifier.fillMaxSize()) {
        //SearchBar(searchUIState.value, mapScreenViewModel)
        SearchBar(searchUIState.value, mapScreenViewModel)
        Box {

            if (dialogUIState.value.isVisible == true && dialogUIState.value.oceanLoaded != null){

                if (dialogUIState.value.oceanLoaded == true){



                    AlertDialogExample(
                        onDismissRequest = {mapScreenViewModel.hideDialog() },
                        onConfirmation = { mapScreenViewModel.hideDialog() },

                        dialogTitle = locationUiState.value.placeName,
                        dialogText = "Vil du se mer info om ${locationUiState.value.placeName}?",
                        icon = Icons.Default.Info
                    )
                }

                else{
                    //Lage en annen popup her
                    AlertDialogExample(
                        onDismissRequest = {mapScreenViewModel.hideDialog() },
                        onConfirmation = { mapScreenViewModel.hideDialog() },
                        dialogTitle = "Ingen data",
                        dialogText = "",
                        icon = Icons.Default.Info
                    )
                }


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
    /*Box {
        if (dialogUIState.value.isVisible == true && dialogUIState.value.oceanLoaded != null){

            if (dialogUIState.value.oceanLoaded == true){



                AlertDialogExample(
                    onDismissRequest = {mapScreenViewModel.hideDialog() },
                    onConfirmation = { mapScreenViewModel.hideDialog() },

                    dialogTitle = locationUiState.value.placeName,
                    dialogText = "Vil du se mer info om ${locationUiState.value.placeName}?",
                    icon = Icons.Default.Info
                )
            }

            else{
                //Lage en annen popup her
                AlertDialogExample(
                    onDismissRequest = {mapScreenViewModel.hideDialog() },
                    onConfirmation = { mapScreenViewModel.hideDialog() },
                    dialogTitle = "Ingen data",
                    dialogText = "",
                    icon = Icons.Default.Info
                )
            }


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
    }*/
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



