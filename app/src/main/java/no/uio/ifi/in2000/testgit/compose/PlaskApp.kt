package no.uio.ifi.in2000.testgit.compose

import HomeScreen
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
        composable("kart") {
            MapScreenMain(navController, "kart")
        }
        composable("innstillinger") {
            SettingsScreen(navController, "innstillinger")
        }
        composable("ActivityScreen/{stedsnavn}/{lat}/{lon}") { backStackEntry ->
            val stedsnavn = backStackEntry.arguments?.getString("stedsnavn")
            val lat = backStackEntry.arguments?.getString("lat")
            val lon = backStackEntry.arguments?.getString("lon")
            stedsnavn ?.let { ActivityScreen(chosenCity = stedsnavn, lat, lon, navController = navController)}
        }
    }
}



/*NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    val state by viewModel.homeUiState.collectAsState()
                    HomeScreen(navController, "home", viewModel, onEvent = viewModel::onEvent)
                }
                composable("kart") {
                    MapScreenMain(navController, "kart")
                }
                composable("innstillinger") {
                }
                composable("ActivityScreen/{stedsnavn}/{lat}/{lon}") { backStackEntry ->
                    val stedsnavn = backStackEntry.arguments?.getString("stedsnavn")
                    val lat = backStackEntry.arguments?.getString("lat")
                    val lon = backStackEntry.arguments?.getString("lon")
                    stedsnavn ?.let { ActivityScreen(chosenCity = stedsnavn, lat, lon, navController = navController)}
                }
            }

 */