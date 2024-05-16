package no.uio.ifi.in2000.testgit.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import no.uio.ifi.in2000.testgit.R
import no.uio.ifi.in2000.testgit.ui.theme.DarkBlue
import no.uio.ifi.in2000.testgit.ui.theme.LightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Text(text = "Plask",
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center)
        },
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.ikon),
                contentDescription = "Tilpasset Ikon",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
        },
        actions = {
            //Filler
                  Box(modifier = Modifier.size(50.dp))
        } ,
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = DarkBlue),
        modifier = Modifier
            .zIndex(1f)
            .padding(4.dp)
    )
}
@Composable
fun BottomBar(navController: NavController?, currentRoute: String) {
    BottomAppBar(
        containerColor = DarkBlue,
        contentColor = Color.White
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)) {
            val routes = listOf("home" to Icons.Filled.Home, "kart" to Icons.Filled.Place, "innstillinger" to Icons.Filled.Settings)
            routes.forEach { (route, icon) ->
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    val iconColor = if (currentRoute == route) LightBlue else Color.White
                    IconButton(onClick = { navController?.navigate(route) }) {
                        Icon(icon, contentDescription = route.capitalize(), tint = iconColor)
                    }
                }
            }
        }
    }
}