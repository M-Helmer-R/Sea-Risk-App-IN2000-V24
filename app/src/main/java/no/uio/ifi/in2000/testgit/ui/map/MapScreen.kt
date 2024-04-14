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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState

import com.mapbox.api.geocoding.v5.GeocodingCriteria
import com.mapbox.api.geocoding.v5.MapboxGeocoding
import com.mapbox.api.geocoding.v5.models.GeocodingResponse
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call

@OptIn(MapboxExperimental::class)
@Composable
fun Mapscreen(){
    var isDialogVisible by remember { mutableStateOf(false) }
    var lat: Double
    var lon: Double
    var placeNameDisplay by remember { mutableStateOf("Error") }

    Box {
        if (isDialogVisible){
            AlertDialogExample(
                onDismissRequest = { isDialogVisible = false },
                onConfirmation = { isDialogVisible = false },
                dialogTitle = placeNameDisplay,
                dialogText = "Vil du se mer info om $placeNameDisplay?",
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

                val reverseGeocode = MapboxGeocoding.builder()
                    .accessToken("sk.eyJ1IjoiYmpvaG9sbW0iLCJhIjoiY2x0eWVwZHp5MGRmaTJrcGpueG8zcTR1MCJ9.zal9bJ3fdxMij0MJB-GvUQ")
                    .query(Point.fromLngLat(lon, lat))
                    .geocodingTypes(GeocodingCriteria.TYPE_ADDRESS)
                    .build()


                reverseGeocode.enqueueCall(object : Callback<GeocodingResponse> {
                    override fun onResponse(call: Call<GeocodingResponse>, response: Response<GeocodingResponse>) {
                        val results = response.body()?.features()
                        if (results != null && results.size > 0) {
                            val firstResult = results[0]
                            val placeName = firstResult.placeName()
                            if (placeName != null) {
                                placeNameDisplay = placeName
                            }
                            isDialogVisible = true
                            Log.i("Geocoding", "Place name: $placeName")
                        } else {
                            Log.i("Geocoding", "No results found.")
                        }
                    }

                    override fun onFailure(call: Call<GeocodingResponse>, throwable: Throwable) {
                        Log.e("Geocoding", "Error: " + throwable.message)
                    }
                })

                println(reverseGeocode.toString()+ "Testgeocode" )
                //isDialogVisible = true





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



