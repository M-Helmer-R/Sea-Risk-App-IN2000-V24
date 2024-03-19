package no.uio.ifi.in2000.testgit.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import no.uio.ifi.in2000.testgit.model.CityDatabase.CityDatabase
import no.uio.ifi.in2000.testgit.ui.Activity.ActivityScreen
import no.uio.ifi.in2000.testgit.ui.Activity.ActivityScreenViewModel

@OptIn(MapboxExperimental::class)
@Composable
fun Homescreen(navController: NavController, homescreenViewModel: HomescreenViewModel = viewModel()){

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
    )

}