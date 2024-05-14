package no.uio.ifi.in2000.testgit.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import no.uio.ifi.in2000.testgit.ui.map.TopBar
import no.uio.ifi.in2000.testgit.ui.theme.DarkBlue
import no.uio.ifi.in2000.testgit.ui.theme.LightBlue
import no.uio.ifi.in2000.testgit.ui.theme.White

@Composable
fun SettingsScreen(navController: NavController?, currentRoute: String = "innstillinger") {
    SettingsContent(navController, currentRoute)
}

@Composable
fun SettingsContent(
    navController: NavController?,
    currentRoute: String,
){
    Scaffold (
        modifier = Modifier.fillMaxSize().background(DarkBlue),
        topBar = { TopBar() },
        bottomBar = {
            BottomBar(navController = navController, currentRoute = currentRoute)
                    },
    ){ padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(DarkBlue),

        ) {
            Text(text = "Innstillinger",
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.titleLarge.copy(color = White)
            )
            SettingsCard()
            PermissionsCard()
        }
    }
}
@Composable
fun SettingsCard(
    context : Context = LocalContext.current
){
    Card(
        modifier = Modifier
            //.width(196.dp)
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = LightBlue),
        onClick = {
            context.startActivity(Intent(ACTION_LOCATION_SOURCE_SETTINGS))
        }
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Posisjon",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.titleMedium.copy(color = White)
            )
            Icon(Icons.Filled.Settings, "Location Settings",
                modifier = Modifier.padding(8.dp).size(32.dp),
                tint = Color.White,
            )
        }
    }
}

@Composable
fun PermissionsCard(
    context : Context = LocalContext.current
){
    Card(
        modifier = Modifier
            //.width(196.dp)
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = LightBlue),
        onClick = {
            val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.fromParts("package", context.packageName, null)
            context.startActivity((intent))
        }
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Tillatelkser",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.titleMedium.copy(color = White)
            )
            Icon(Icons.Filled.Settings, "Innstillinger",
                modifier = Modifier.padding(8.dp).size(32.dp),
                tint = Color.White,
            )
        }
    }
}