package no.uio.ifi.in2000.testgit.ui.map

import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.ViewAnnotationOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.viewannotation.annotationAnchor
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions

import no.uio.ifi.in2000.testgit.data.map.GeocodingPlacesResponse
import no.uio.ifi.in2000.testgit.ui.theme.DarkBlue
import no.uio.ifi.in2000.testgit.ui.theme.LightBlue


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
    navController: NavController,


){
    Column(modifier = Modifier.fillMaxSize()) {
        val mapViewportState = mapScreenViewModel.mapViewportState
        Box {
            MapboxMap(
                Modifier.fillMaxSize(),
                /*mapViewportState = MapViewportState().apply {
                    setCameraOptions {
                        zoom(3.7)
                        center(Point.fromLngLat(11.49537, 64.01487))
                        pitch(0.0)
                        bearing(0.0)
                    }
                },*/

                mapViewportState = mapViewportState,
                onMapClickListener =  { point ->
                    Log.i("Map Click", "Lat: ${point.latitude()}, Lon: ${point.longitude()}")
                    oceanForeCastUIState.value.loaded = Loaded.LOADING
                    mapScreenViewModel.updateMapClickLocation(point)
                    mapScreenViewModel.loadPlaceName2(point.longitude(), point.latitude() )
                    keyboardController?.hide()
                    val cameraOptions = CameraOptions.Builder()
                        .center(Point.fromLngLat(point.longitude(), point.latitude())) // set center
                        .zoom(14.0)
                        .build()

                    mapViewportState.flyTo(cameraOptions)
                    true
                },
            ) {
                val point = mapScreenViewModel.mapClickLocation.collectAsState().value

                if (locationUIState.value.loaded == Loaded.SUCCESS && point != null && oceanForeCastUIState.value.loaded == Loaded.SUCCESS) {
                    ViewAnnotation(
                        options = ViewAnnotationOptions.Builder()
                            .geometry(point)
                            .annotationAnchor {
                                anchor(ViewAnnotationAnchor.BOTTOM)
                            }
                            .build()
                    ) {
                        if(true){ //Få inn testene til Kriss
                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .width(200.dp),
                                colors = CardDefaults.cardColors(containerColor = DarkBlue),
                                shape = RoundedCornerShape(18.dp)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(locationUIState.value.placeName, color = Color.White)
                                    Button(
                                        onClick = { navController.navigate("ActivityScreen/${locationUIState.value.placeName}/${point.latitude()}/${point.longitude()}") }, //Få inn navn på kommune Kriss, tror ikke point.l() ikke funker
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = LightBlue,
                                            contentColor = Color.White
                                        )
                                    ) {
                                        Text("Naviger", color = Color.White)
                                    }
                                    //Opprette en nei knapp som closer
                                }
                            }
                        }
                        else {
                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .width(200.dp),
                                colors = CardDefaults.cardColors(containerColor = DarkBlue),
                                shape = RoundedCornerShape(18.dp)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(
                                        "Ingen data, trykk på område nærme vann",
                                        color = Color.White
                                    )
                                    Button(
                                        onClick = { mapScreenViewModel.hidePopup() },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = LightBlue,
                                            contentColor = Color.White
                                        )
                                    ) {
                                        Text("Lukk", color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


