package no.uio.ifi.in2000.testgit.ui.map

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import no.uio.ifi.in2000.testgit.ui.theme.DarkBlue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchUIState: SearchUIState, mapScreenViewModel: MapScreenViewModel, keyboardController: SoftwareKeyboardController?, navController: NavController){
    var text by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 20.dp),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
    colors = CardDefaults.cardColors(containerColor = DarkBlue.copy(alpha = 0.8F))
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {
            TextField(
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        if (searchUIState.geocodingPlacesResponse?.features?.isNotEmpty() == true) {
                            //Endre dette til funksjon som skal gå til kartet med popup
                            val firstFeature = searchUIState.geocodingPlacesResponse!!.features.first()
                            navController.navigate("ActivityScreen/${firstFeature.properties.name}/${firstFeature.properties.coordinates.lat}/${firstFeature.properties.coordinates.lon}")
                        }
                    }
                ),
                value = text,
                onValueChange = {
                    text = it
                    expanded = text.isNotEmpty()
                    if (expanded) {
                        mapScreenViewModel.loadSearchUIState(text)
                    } else {
                        mapScreenViewModel.unloadSearchUIState()
                    }
                },
                label = { Text("Søk etter sted", color = Color.White) },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = DarkBlue,
                    containerColor = Color.Transparent
                )
            )

            if (expanded && searchUIState.geocodingPlacesResponse != null) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(searchUIState.geocodingPlacesResponse!!.features) {
                        LocationSuggestionCardClickable(
                            lat = it.properties.coordinates.lat,
                            lon = it.properties.coordinates.lon,
                            place = it.properties.name,
                            navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LocationSuggestionCardClickable(lat: Double, lon: Double, place: String, navController: NavController) {
    Card(
        //Endre dette til funksjon som skal gå til kartet med popup
        onClick = { navController.navigate("ActivityScreen/$place/$lat/$lon") },
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = DarkBlue.copy(alpha = 0F))

    ) {
        Text(place, color = Color.White, modifier = Modifier.padding(13.dp))
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



