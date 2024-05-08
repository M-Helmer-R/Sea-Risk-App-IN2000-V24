package no.uio.ifi.in2000.testgit.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import no.uio.ifi.in2000.testgit.ui.map.TopBar

@Composable
fun SettingsScreen(navController: NavController?, currentRoute: String = "innstillinger") {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar()
        Text("Dette er Innstillinger-skjermen.", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(16.dp))
        BottomBar(navController, currentRoute)
    }
}