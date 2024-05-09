package no.uio.ifi.in2000.testgit.compose

import HomeScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import no.uio.ifi.in2000.testgit.ui.home.HomeViewModel
import no.uio.ifi.in2000.testgit.ui.home.SettingsScreen
import no.uio.ifi.in2000.testgit.ui.map.MapScreenMain

@Composable
fun PlaskApp(
){
    val navController = rememberNavController()
    PlaskAppHost(
        navController = navController
    )
}

@Composable
fun PlaskAppHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController, "home")
        }
        composable("kart") {
            MapScreenMain(navController, "kart")
        }
        composable("innstillinger") {
            SettingsScreen(navController, "innstillinger")
        }
    }
}
