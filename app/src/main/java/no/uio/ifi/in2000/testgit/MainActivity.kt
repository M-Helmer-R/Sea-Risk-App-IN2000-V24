package no.uio.ifi.in2000.testgit


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
import androidx.compose.ui.platform.LocalContext
import no.uio.ifi.in2000.testgit.compose.PlaskApp


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaskApp()
        }
    }
}