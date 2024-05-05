package no.uio.ifi.in2000.testgit.ui.map

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState

import no.uio.ifi.in2000.testgit.data.map.GeocodingPlacesResponse

@Composable
fun LocationSuggestionCardClickable(lat: Double, lon: Double, place: String, navController: NavController){
    Card(
        // hvorfor er det navigation til instillinger her?
        onClick = {navController.navigate("ActivityScreen/${place}/${lat}/${lon}") }
    ){
        Text(place)

    }
}

@Composable
fun SearchBar(searchUIState: SearchUIState, mapScreenViewModel: MapScreenViewModel, keyboardController: SoftwareKeyboardController?, navController: NavController){
    var text by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }


    Column(){
        TextField(
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
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
            label = {Text("Søk etter sted")},
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .width(200.dp)
                .height(56.dp)

        )

        if (expanded && searchUIState.geocodingPlacesResponse != null){
            LazyColumn {
                //To be replaced with clickable card which navigates
                items(searchUIState.geocodingPlacesResponse!!.features){
                    //Text(it.properties.name)
                    LocationSuggestionCardClickable(lat = it.properties.coordinates.lat, lon = it.properties.coordinates.lon, place = it.properties.name, navController)
                }
            }
        }

    }

}

@OptIn(MapboxExperimental::class)
@Composable
fun Mapscreen(
    mapScreenViewModel: MapScreenViewModel,
    locationUIState: State<LocationUIState>,
    dialogUIState: State<DialogUIState>,
    searchUIState: State<SearchUIState>,
    oceanForeCastUIState: State<OceanForeCastUIState>,
    keyboardController: SoftwareKeyboardController?,
    navController: NavController
){

    // er lat og lon her fylt inn allerede
    var lat: Double = 7.0
    var lon: Double = 8.0



    Column(modifier = Modifier.fillMaxSize()) {
        //SearchBar(searchUIState.value, mapScreenViewModel)
        //SearchBar(searchUIState.value, mapScreenViewModel, keyboardController)
        Box {



            if (dialogUIState.value.isVisible == true && dialogUIState.value.oceanLoaded != null){

                if (dialogUIState.value.oceanLoaded == true){



                    AlertDialogExample(
                        onDismissRequest = {mapScreenViewModel.hideDialog() },

                        onConfirmation = { mapScreenViewModel.hideDialog()
                            navController.navigate(
                                "ActivityScreen/${locationUIState.value.placeName}/${locationUIState.value.lat}/${locationUIState.value.lon}")},
                        // forstaa hvorfor det ligger en nav til instillinger her
                        //onConfirmation = {navController?.navigate("innstillinger"); mapScreenViewModel.hideDialog() },


                        dialogTitle = locationUIState.value.placeName,
                        dialogText = "Vil du se mer info om ${locationUIState.value.placeName}?",
                        icon = Icons.Default.Info
                    )
                }

                else{
                    //Lage en annen popup her
                    AlertDialogExample(
                        onDismissRequest = {mapScreenViewModel.hideDialog() },
                        onConfirmation = {
                            mapScreenViewModel.hideDialog()

                                         },
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
                    keyboardController?.hide()

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



