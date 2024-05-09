@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import no.uio.ifi.in2000.testgit.ui.home.AddCityCard
import no.uio.ifi.in2000.testgit.ui.home.Dialog.AddCityDialog
import no.uio.ifi.in2000.testgit.ui.home.BottomBar
import no.uio.ifi.in2000.testgit.ui.home.Dialog.LocationDialog
import no.uio.ifi.in2000.testgit.ui.home.Dialog.ManualLocationDialog
import no.uio.ifi.in2000.testgit.ui.home.HomeEvent
import no.uio.ifi.in2000.testgit.ui.home.HomeUiState
import no.uio.ifi.in2000.testgit.ui.home.HomeViewModel
import no.uio.ifi.in2000.testgit.ui.home.HorizontalCard
import no.uio.ifi.in2000.testgit.ui.home.MainCard
import no.uio.ifi.in2000.testgit.ui.map.TopBar
import no.uio.ifi.in2000.testgit.ui.theme.DarkBlue
import no.uio.ifi.in2000.testgit.ui.theme.White

@Composable
fun HomeScreen(
    navController : NavController?,
    currentRoute : String,
    homeViewModel : HomeViewModel = viewModel(factory = HomeViewModel.Factory),
) {
    val onEvent = homeViewModel :: onEvent

    val homeUiState: HomeUiState by homeViewModel.homeUiState.collectAsState()

    val containerModifier: Modifier = Modifier
        .background(DarkBlue)
        .fillMaxSize()
        .padding(8.dp)

    Scaffold(
        containerColor = DarkBlue,
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomBar(
                navController = navController,
                currentRoute = currentRoute
            )
        },

        ) {
        innerPadding ->
        LazyColumn (
            modifier = Modifier
                .padding(innerPadding)
                .background(DarkBlue)
        ) {
            item{
                if (homeUiState.isAddingCity) {
                    AddCityDialog(
                        onEvent = onEvent,
                        homeUiState = homeUiState,
                    )
                }
                if (homeUiState.locationDialog) {
                    LocationDialog(onEvent = onEvent, homeViewModel)
                }
                if (homeUiState.manualLocationDialog){
                    ManualLocationDialog(onEvent = onEvent)
                }
            }
            item{
                HorizontalContent(homeUiState, onEvent, containerModifier)
            }
            item{
                FavoriteContent(homeUiState, onEvent, containerModifier)
            }
        }

    }
}

@Composable
fun HorizontalContent(
    homeUiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier,
    ){

    Column (
        modifier = modifier,
    ){
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Nærmeste aktivitetsplasser:",
            style = MaterialTheme.typography.headlineSmall.copy(color = White)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            items(homeUiState.nearestCities.keys.toList()) { city ->
                HorizontalCard(city, homeUiState.nearestCities.getValue(city), onEvent)
            }
        }

        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            Text(
                text = "Din posisjon: ${homeUiState.userLat}, ${homeUiState.userLon}",
                style = MaterialTheme.typography.labelSmall.copy(color = White)
            )
            Button(
                //modifier = Modifier.size(64.dp),
                onClick = {
                    Log.w("Home_SCREEN: ", "Before ${homeUiState.userLat}")
                    onEvent(HomeEvent.showLocationDialog)
                    Log.w("Home_SCREEN", "After ${homeUiState.userLat}")
                }
            ) {
                Icon(
                    //modifier = Modifier.size(8.dp),
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh position"
                )
            }
        }
    }
}


@Composable
fun FavoriteContent(
    homeUiState : HomeUiState,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier,
) {
    Column (
        modifier = modifier
    ){
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Favoritter",
            style = MaterialTheme.typography.headlineSmall.copy(color = White)
        )

        homeUiState.favorites.map { city ->
            MainCard(city = city, onEvent = onEvent)
        }

        AddCityCard(onEvent)

    }
}
