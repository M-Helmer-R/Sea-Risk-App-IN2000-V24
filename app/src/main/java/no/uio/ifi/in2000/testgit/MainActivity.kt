package no.uio.ifi.in2000.testgit

import BottomBar
import HomeScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import no.uio.ifi.in2000.testgit.ui.map.MapScreen
import no.uio.ifi.in2000.testgit.ui.map.TopBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(navController, "home")
                }
                composable("kart") {
                    MapScreen(navController, "kart")
                }
                composable("innstillinger") {
                    InnstillingerScreen(navController, "innstillinger")
                }
            }
        }
    }
}

@Composable
fun InnstillingerScreen(navController: NavController?, currentRoute: String = "innstillinger") {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar()
        Text("Dette er Innstillinger-skjermen.", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(16.dp))
        BottomBar(navController, currentRoute)
    }
}
