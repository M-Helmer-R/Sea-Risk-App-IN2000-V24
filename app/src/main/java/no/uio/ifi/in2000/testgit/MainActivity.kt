package no.uio.ifi.in2000.testgit

import BottomBar
import HomeScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import no.uio.ifi.in2000.testgit.data.room.CityDatabase
import no.uio.ifi.in2000.testgit.ui.Activity.ActivityScreen
import no.uio.ifi.in2000.testgit.ui.home.HomeViewModel
import no.uio.ifi.in2000.testgit.ui.map.MapScreenMain
import no.uio.ifi.in2000.testgit.ui.map.TopBar

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            CityDatabase::class.java,
            "cities.db"
        ).createFromAsset("database/cities100.db")
            .build()
    }

    private val viewModel by viewModels<HomeViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HomeViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    val state by viewModel.homeUiState.collectAsState()
                    HomeScreen(navController, "home", viewModel, onEvent = viewModel::onEvent)
                }
                composable("kart") {
                    MapScreenMain(navController, "kart")
                }
                composable("innstillinger") {
                    ActivityScreen("Oslo",navController)
                }
                composable("ActivityScreen/{stedsnavn}") { backStackEntry ->
                    backStackEntry.arguments?.getString("stedsnavn")
                        ?.let { ActivityScreen(chosenCity = it, navController = navController)}
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
