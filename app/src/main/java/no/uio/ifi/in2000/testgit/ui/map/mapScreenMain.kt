package no.uio.ifi.in2000.testgit.ui.map

import BottomBar
import DarkBlue
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import no.uio.ifi.in2000.testgit.R

@Composable
fun MapScreen(navController: NavController?, currentRoute: String) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar()
        ShowMap(modifier = Modifier.weight(1f))
        BottomBar(navController, currentRoute)
    }
}
@Composable
fun ShowMap(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize().background(Color.Gray)) {
        Text("Kart kommer her", color = Color.White, modifier = Modifier.align(Alignment.Center))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Text(text = "Plask", color = Color.White, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
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
            IconButton(onClick = { /* Handling når ikonet klikkes */ }) {
                Icon(Icons.Filled.Search, contentDescription = "Innstillinger", modifier = Modifier.size(50.dp), tint= White)
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = DarkBlue),
        modifier = Modifier.zIndex(1f)
    )
}

