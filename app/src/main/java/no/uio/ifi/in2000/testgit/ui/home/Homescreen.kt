import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import no.uio.ifi.in2000.testgit.R

data class CityInfo(
    val name: String,
    val distance: String,
    val icons: List<Int>
)

val cities = listOf(
    CityInfo("Oslo", "500km", listOf(1, 2)),
    CityInfo("Bergen", "300km", listOf(1, 2, 3)),
    CityInfo("Trondheim", "250km", listOf(1)),
    CityInfo("Stavanger", "450km", listOf(1, 2, 3, 4)),
    CityInfo("Sogndal", "0km", listOf(1, 2, 3, 4))
)
val DarkBlue = Color(0xFF013749)
val LightBlue = Color(0xFF0C8891)
val White = Color(0xFFFFFFFF)

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Homescreen(navController: NavController?) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(DarkBlue)) {
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
                    Icon(Icons.Filled.Settings, contentDescription = "Innstillinger", modifier = Modifier.size(50.dp), tint= White)
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF013749))
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(top = 64.dp)
        ) {
            Text(
                text = "Søkefunksjon incoming?",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = White,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Nærmeste aktivitetsplasser:",
                style = MaterialTheme.typography.headlineSmall.copy(color = White),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(cities) { city ->
                    CityCard(city)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Favoritter:",
                style = MaterialTheme.typography.headlineSmall.copy(color = White),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(cities) { city ->
                    CityCard1(city)
                }
            }
        }
    }
}


@Composable
fun CityCard(city: CityInfo) {
    Card(
        modifier = Modifier
            .size(width = 140.dp, height = 90.dp)
            .padding(0.dp),
        colors = CardDefaults.cardColors(containerColor = LightBlue),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .background(Color.Transparent),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = city.name, style = MaterialTheme.typography.titleMedium.copy(color = White))
            Text(text = city.distance, style = MaterialTheme.typography.bodySmall.copy(color = White))
            Row {
                city.icons.forEach { iconId ->
                    Icon(imageVector = Icons.Filled.Home, contentDescription = null, modifier = Modifier.size(24.dp), tint = White)
                }
            }
        }
    }
}

@Composable
fun CityCard1(city: CityInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(4.dp),

        colors = CardDefaults.cardColors(containerColor = LightBlue),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(text = city.name, style = MaterialTheme.typography.titleMedium.copy(color = White))
                Text(text = city.distance, style = MaterialTheme.typography.bodySmall.copy(color = White))
            }
            Row(modifier = Modifier.padding(8.dp)) {
                city.icons.forEach { iconId ->
                    Icon(imageVector = Icons.Filled.Place, contentDescription = null, modifier = Modifier.size(24.dp), tint = White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomescreen() {
    Homescreen(navController = null)
}
