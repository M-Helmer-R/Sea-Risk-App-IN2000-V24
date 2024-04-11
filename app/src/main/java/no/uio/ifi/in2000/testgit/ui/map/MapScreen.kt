package no.uio.ifi.in2000.testgit.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.plugin.gestures.OnMapClickListener

@OptIn(MapboxExperimental::class)
@Composable
fun Mapscreen(){

    MapboxMap(

        Modifier.fillMaxSize(),
        mapViewportState = MapViewportState().apply {
            setCameraOptions {
                zoom(3.7)
                center(Point.fromLngLat(11.49537, 64.01487 ))
                pitch(0.0)
                bearing(0.0)
            }


        },
        onMapClickListener= OnMapClickListener { point ->
            val lat = point.latitude()
            val long = point.longitude()
            println(lat)





            true
        },

    ){

    }
}