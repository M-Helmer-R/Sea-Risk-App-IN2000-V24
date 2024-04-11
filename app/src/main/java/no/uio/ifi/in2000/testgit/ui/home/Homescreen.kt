package no.uio.ifi.in2000.testgit.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import no.uio.ifi.in2000.testgit.ui.map.Mapscreen


@OptIn(MapboxExperimental::class)
@Composable
fun Homescreen(navController: NavController, homescreenViewModel: HomescreenViewModel = viewModel()){


    Mapscreen()
    //PointAnnotationGroup(annotations = )


}

