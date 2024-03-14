package no.uio.ifi.in2000.testgit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import no.uio.ifi.in2000.testgit.ui.Activity.ActivityScreen
import no.uio.ifi.in2000.testgit.ui.Activity.ActivityScreenViewModel
import no.uio.ifi.in2000.testgit.ui.home.Homescreen
import no.uio.ifi.in2000.testgit.ui.theme.TestGitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestGitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Homescreen") {
        composable("Homescreen") { Homescreen(navController)}

        composable("ActivityScreen/{cityName}") {backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("cityName")
            if (cityName.equals("Oslo")) {
                val lat = "59.9"
                val lon = "10.7"
                ActivityScreen(lat, lon, navController, ActivityScreenViewModel(lat, lon, "Oslo"))
            } else{ //bergen
                ActivityScreen("60.4", "5.32" , navController, ActivityScreenViewModel("60.4", "5.32", "Bergen"))
            }

        }
    }

}