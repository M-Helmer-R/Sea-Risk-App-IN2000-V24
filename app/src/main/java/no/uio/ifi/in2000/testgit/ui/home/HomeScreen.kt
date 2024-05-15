@file:OptIn(ExperimentalPermissionsApi::class)

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import no.uio.ifi.in2000.testgit.ui.home.AddCityCard
import no.uio.ifi.in2000.testgit.ui.BottomBar
import no.uio.ifi.in2000.testgit.ui.home.HomeEvent
import no.uio.ifi.in2000.testgit.ui.home.HomeUiState
import no.uio.ifi.in2000.testgit.ui.home.HomeViewModel
import no.uio.ifi.in2000.testgit.ui.home.HorizontalCard
import no.uio.ifi.in2000.testgit.ui.home.MainCard
import no.uio.ifi.in2000.testgit.ui.home.dialog.AddCityDialog
import no.uio.ifi.in2000.testgit.ui.home.dialog.DeniedPermissionDialog
import no.uio.ifi.in2000.testgit.ui.home.dialog.DisabledLocationDialog
import no.uio.ifi.in2000.testgit.ui.home.dialog.LocationButton
import no.uio.ifi.in2000.testgit.ui.home.dialog.LocationStatus
import no.uio.ifi.in2000.testgit.ui.home.dialog.PermissionRationaleDialog
import no.uio.ifi.in2000.testgit.ui.map.TopBar
import no.uio.ifi.in2000.testgit.ui.theme.DarkBlue
import no.uio.ifi.in2000.testgit.ui.theme.White
@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION]
)
@Composable
fun HomeScreen(
    navController : NavController,
    currentRoute : String,
    context : Context,
    homeViewModel : HomeViewModel = viewModel(factory = HomeViewModel.Factory),

) {
    val onEvent = homeViewModel :: onEvent
    val homeUiState: HomeUiState by homeViewModel.homeUiState.collectAsState()
    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

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
        ) { innerPadding ->
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
                if (homeUiState.permissionDialog){
                    PermissionRationaleDialog(onEvent = onEvent)
                }
                if (homeUiState.deniedLocationDialog){
                    DeniedPermissionDialog(onEvent)
                }
                if (homeUiState.disabledLocationDialog){
                    DisabledLocationDialog(onEvent = onEvent)
                }
            }
            item{
                HorizontalContent(
                    homeUiState = homeUiState,
                    onEvent = onEvent,
                    modifier = containerModifier,
                    locationPermissionState = locationPermissionState, context,
                    navController
                )
            }
            item{
                FavoriteContent(homeUiState, onEvent, containerModifier)
            }
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun HorizontalContent(
    homeUiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier,
    locationPermissionState : MultiplePermissionsState,
    context : Context,
    navController: NavController
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
                HorizontalCard(city, homeUiState.nearestCities.getValue(city), onEvent, navController)
            }
        }
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            LocationStatus(
                locationState = locationPermissionState,
                homeUiState = homeUiState
            )
            LocationButton(
                locationPermissionState = locationPermissionState,
                context = context,
                onEvent = onEvent
            )
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
            MainCard(
                city = city,
                onEvent = onEvent
            )
        }
        AddCityCard(onEvent = onEvent)

    }
}

