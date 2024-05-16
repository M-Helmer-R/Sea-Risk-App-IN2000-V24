@file:OptIn(ExperimentalPermissionsApi::class)

package no.uio.ifi.in2000.testgit.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import no.uio.ifi.in2000.testgit.data.room.City
import no.uio.ifi.in2000.testgit.ui.BottomBar
import no.uio.ifi.in2000.testgit.ui.TopBar
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
    locationViewModel : LocationViewModel = viewModel(factory = LocationViewModel.Factory),
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

    LaunchedEffect(key1 = locationPermissionState.allPermissionsGranted) {
        if (locationPermissionState.allPermissionsGranted) {
            onEvent(HomeEvent.UpdateNearest)
            locationViewModel.location.observe(context as LifecycleOwner) { location ->
                location?.let {
                    onEvent(HomeEvent.SetUserPosition(lon = it.longitude, lat = it.latitude))
                    onEvent(HomeEvent.UpdateNearest)
                } ?: run {
                    onEvent(HomeEvent.ShowDisabledLocationDialog)
                }
            }
            locationViewModel.fetchLocation()
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            onEvent(HomeEvent.ShowDeniedPermissionDialog)
        } else {
            onEvent(HomeEvent.ShowPermissionDialog)
        }
    }

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
                if (homeUiState.permissionDialog){
                    PermissionRationaleDialog(
                        locationPermissionState = locationPermissionState,
                        locationViewModel = locationViewModel,
                        onEvent = onEvent
                    )
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
                    nearestCities = homeUiState.nearestCities,
                    modifier = containerModifier,
                    navController = navController
                )
            }
            item{
                FavoriteContent(
                    favorites = homeUiState.favorites,
                    onEvent = onEvent,
                    modifier = containerModifier,
                    navController = navController
                )
            }
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun HorizontalContent(
    modifier: Modifier,
    navController: NavController,
    nearestCities : Map<City, Double>,
){
    Column (
        modifier = modifier,
    ){
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Nærmeste aktivitetsplasser",
            style = MaterialTheme.typography.headlineSmall.copy(color = White)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(nearestCities.keys.toList()) { city ->
                HorizontalCard(city, nearestCities.getValue(city), navController)
            }
        }
    }
}

@Composable
fun FavoriteContent(
    favorites : List<City>,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier,
    navController: NavController
) {
    Column (
        modifier = modifier
    ){
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Favoritter",
            style = MaterialTheme.typography.headlineSmall.copy(color = White)
        )
        favorites.map { city ->
            MainCard(
                city = city,
                onEvent = onEvent,
                navController = navController
            )
        }
        AddCityCard()
    }
}

