package no.uio.ifi.in2000.testgit.ui.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import no.uio.ifi.in2000.testgit.data.map.GeocodingPlacesResponse
import no.uio.ifi.in2000.testgit.ui.BottomBar
import no.uio.ifi.in2000.testgit.ui.TopBar
import no.uio.ifi.in2000.testgit.ui.theme.DarkBlue

@Composable
fun MapScreenMain(navController: NavController, currentRoute: String, mapScreenViewModel: MapScreenViewModel = viewModel()) {

    val locationUiState = mapScreenViewModel.locationUIState.collectAsState()
    val dialogUIState = mapScreenViewModel.dialogUIState.collectAsState()
    val searchUIState = mapScreenViewModel.searchUIState.collectAsState()
    val oceanForeCastUIState = mapScreenViewModel.oceanForeCastUIState.collectAsState()
    val searchBarUIState = mapScreenViewModel.searchBarUIState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = Modifier
        .fillMaxSize()
        .background(DarkBlue)) {
        TopBar()
        //SearchBar(searchUIState.value, mapScreenViewModel, keyboardController = keyboardController)
        ShowMap(modifier = Modifier.weight(1f), mapScreenViewModel, locationUiState, dialogUIState, searchUIState, oceanForeCastUIState, keyboardController, navController, searchBarUIState)
        BottomBar(navController, currentRoute)
    }
}
@Composable
fun ShowMap(
    modifier: Modifier = Modifier,
    mapScreenViewModel: MapScreenViewModel,
    locationUIState: State<LocationUIState>,
    dialogUIState: State<DialogUIState>,
    searchUIState: State<SearchUIState>,
    oceanForeCastUIState: State<OceanForeCastUIState>,
    keyboardController: SoftwareKeyboardController?,
    navController: NavController,
    searchBarUIState: State<SearchBarUIState>
) {

    Box(modifier = modifier
        .fillMaxSize()
        .background(Color.Red)) {

        //SearchBar(searchUIState.value, mapScreenViewModel, keyboardController = keyboardController)
        Mapscreen(mapScreenViewModel = mapScreenViewModel, locationUIState = locationUIState, dialogUIState = dialogUIState, searchUIState = searchUIState, oceanForeCastUIState = oceanForeCastUIState, keyboardController, navController )

        Box(
            modifier = Modifier.align(Alignment.TopCenter),
            contentAlignment = Alignment.TopCenter
        ) {
            SearchBar(searchUIState = searchUIState.value, mapScreenViewModel = mapScreenViewModel, keyboardController = keyboardController, navController, mapScreenViewModel.mapViewportState, searchBarUIState)
        }
    }
}

data class SearchUIState(var geocodingPlacesResponse: GeocodingPlacesResponse?)