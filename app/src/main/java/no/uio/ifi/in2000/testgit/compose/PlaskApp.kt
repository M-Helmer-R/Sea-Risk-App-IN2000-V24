package no.uio.ifi.in2000.testgit.compose

import no.uio.ifi.in2000.testgit.ui.home.HomeScreen
import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import no.uio.ifi.in2000.testgit.ui.Activity.ActivityScreen
import no.uio.ifi.in2000.testgit.ui.SettingsScreen
import no.uio.ifi.in2000.testgit.ui.map.MapScreenMain
@SuppressLint("MissingPermission")
@Composable
fun PlaskApp(
    context : Context = LocalContext.current
){
    val navController = rememberNavController()
    PlaskAppHost(
        navController = navController,
        context = context,
    )
}

@SuppressLint("MissingPermission")
@Composable
fun PlaskAppHost(
    navController: NavHostController,
    context : Context
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController, "home", context)
        }
        composable("map") {
            MapScreenMain(navController, "map")
        }
        composable("settings") {
            SettingsScreen(navController, "settings")
        }
        composable("ActivityScreen/{placename}/{lat}/{lon}") { backStackEntry ->
            val placename = backStackEntry.arguments?.getString("placename")
            val lat = backStackEntry.arguments?.getString("lat")
            val lon = backStackEntry.arguments?.getString("lon")
            placename ?.let { ActivityScreen(chosenCity = placename, lat, lon, navController = navController)}
        }
    }
}



/*NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    val state by viewModel.homeUiState.collectAsState()
                    no.uio.ifi.in2000.testgit.ui.home.HomeScreen(navController, "home", viewModel, onEvent = viewModel::onEvent)
                }
                composable("map") {
                    MapScreenMain(navController, "map")
                }
                composable("settings") {
                }
                composable("ActivityScreen/{placename}/{lat}/{lon}") { backStackEntry ->
                    val placename = backStackEntry.arguments?.getString("placename")
                    val lat = backStackEntry.arguments?.getString("lat")
                    val lon = backStackEntry.arguments?.getString("lon")
                    placename ?.let { ActivityScreen(chosenCity = placename, lat, lon, navController = navController)}
                }
            }

 */