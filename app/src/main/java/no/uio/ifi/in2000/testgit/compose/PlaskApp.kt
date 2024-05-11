package no.uio.ifi.in2000.testgit.compose

import HomeScreen
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import no.uio.ifi.in2000.testgit.ui.SettingsScreen
import no.uio.ifi.in2000.testgit.ui.map.MapScreenMain

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
    }
}