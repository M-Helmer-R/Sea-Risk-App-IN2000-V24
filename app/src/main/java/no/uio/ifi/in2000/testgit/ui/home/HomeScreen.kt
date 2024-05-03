@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import no.uio.ifi.in2000.testgit.R
import no.uio.ifi.in2000.testgit.data.room.City
import no.uio.ifi.in2000.testgit.ui.home.AddCityDialog
import no.uio.ifi.in2000.testgit.ui.home.DeniedPermissionDialog
import no.uio.ifi.in2000.testgit.ui.home.HomeEvent
import no.uio.ifi.in2000.testgit.ui.home.HomeUiState
import no.uio.ifi.in2000.testgit.ui.home.HomeViewModel
import no.uio.ifi.in2000.testgit.ui.home.PermissionDialog
import no.uio.ifi.in2000.testgit.ui.map.TopBar
import no.uio.ifi.in2000.testgit.ui.theme.DarkBlue
import no.uio.ifi.in2000.testgit.ui.theme.LightBlue
import no.uio.ifi.in2000.testgit.ui.theme.LightBlueShade1
import no.uio.ifi.in2000.testgit.ui.theme.White

@Composable
fun HomeScreen(
    navController : NavController?,
    currentRoute : String,
    homeViewModel : HomeViewModel,
    onEvent: (HomeEvent) -> Unit
) {

    val homeUiState: HomeUiState by homeViewModel.homeUiState.collectAsState()

    val containerModifier: Modifier = Modifier
        .background(DarkBlue)
        .fillMaxSize()
        .padding(8.dp)


    Scaffold(
        containerColor = DarkBlue,
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomBar(
                navController = navController,
                currentRoute = currentRoute
            )
        },

        ) { innerPadding -> Surface (
            modifier = Modifier.padding(innerPadding).background(DarkBlue)
        ) {
            MainContent(homeUiState, onEvent, containerModifier)
        }
    }


}
@Composable
fun MainContent(
    homeUiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit,
    modifier : Modifier
){
    LazyColumn{
        item {
            HorizontalContent(homeUiState, onEvent, modifier)
        }
        item {
            FavoriteContent(homeUiState, onEvent, modifier)
        }
    }
}
@Composable
fun HorizontalContent(
    homeUiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier,
    ){

    Column (
        modifier = modifier,
    ){
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Nærmeste aktivitetsplasser:",
            style = MaterialTheme.typography.headlineSmall.copy(color = White)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            items(homeUiState.nearestCities.keys.toList()) { city ->
                HorizontalCard(city, homeUiState.nearestCities.getValue(city), onEvent)
            }
        }

        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            Text(
                text = "Din posisjon: ${homeUiState.userLat}, ${homeUiState.userLon}",
                style = MaterialTheme.typography.labelSmall.copy(color = White)
            )
            Button(
                //modifier = Modifier.size(64.dp),
                onClick = {
                    Log.w("Home_SCREEN: ", "Before ${homeUiState.userLat}")
                    onEvent(HomeEvent.showPermissionDialog)
                    Log.w("Home_SCREEN", "After ${homeUiState.userLat}")
                }
            ) {
                Icon(
                    //modifier = Modifier.size(8.dp),
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh position"
                )
            }
        }
    }
}


@Composable
fun FavoriteContent(
    homeUiState : HomeUiState,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier,
) {
    Column (
        modifier = modifier
    ){
        if (homeUiState.isAddingCity) {
            AddCityDialog(onEvent = onEvent)
        }

        if (homeUiState.askingPermission){
            PermissionDialog(onEvent = onEvent)
        }

        if (homeUiState.deniedPermission) {
            DeniedPermissionDialog(onEvent = onEvent)
        }

        Text(
            modifier = Modifier.padding(8.dp),
            text = "Favoritter",
            style = MaterialTheme.typography.headlineSmall.copy(color = White)
        )

        homeUiState.preloaded.map { city ->
            MainCard(city = city, onEvent = onEvent)
        }

        AddCityCard(onEvent)

    }
}

@Composable
fun MainCard(
    city: City,
    onEvent: (HomeEvent) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        colors =  CardDefaults.cardColors(containerColor = LightBlue),
        shape = MaterialTheme.shapes.medium,
        onClick = {
            //Legg inn navigasjon her
            TODO()
            //onEvent(HomeEvent.OpenActivity(city))
        }
    ) {
        Row (
            modifier = Modifier
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Text(
                        text = city.name,
                        style = MaterialTheme.typography.titleMedium.copy(color = White)
                    )
                    if (city.customized == 1) {
                        Icon(
                            imageVector = Icons.Filled.Build,
                            contentDescription = "Custom ",
                            modifier = Modifier.size(12.dp),
                            tint = Color.White
                        )
                    }
                }
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.White)
                        ) {
                            append("Lon: ")
                        }
                        append("${city.lon} ")
                        append("   ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.White)
                        ) {
                            append("Lat: ")
                        }
                        append("${city.lat} ")
                    }
                )
            }

            //Kart
        }
    }
}

@Composable
fun HorizontalCard(
    city: City,
    distance : Double,
    onEvent: (HomeEvent) -> Unit
) {
    Card(
        modifier = Modifier
            //.width(196.dp)
            .fillMaxSize()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = LightBlue),
        shape = MaterialTheme.shapes.medium,
        onClick = {
            //Legg inn navigasjon her
            TODO()
            //onEvent(HomeEvent.OpenActivity(city))
        }
    ) {
        Row (
            modifier = Modifier
                .padding(6.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = city.name,
                    style = MaterialTheme.typography.titleMedium.copy(color = White)
                )

                Text(
                    text = String.format("%.2f", distance) + " km",
                    style = MaterialTheme.typography.bodySmall.copy(color = White)
                )
                /*
            Row(modifier = Modifier.padding(8.dp)) {
                city.icons.forEach { iconId ->
                    Icon(imageVector = Icons.Filled.Place, contentDescription = null, modifier = Modifier.size(24.dp), tint = White)
                }
            }
             */
            }
        }
    }
}

@Composable
fun AddCityCard(onEvent: (HomeEvent) -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            //.size(width = 140.dp, height = 90.dp)
            .padding(12.dp),
        colors = CardDefaults.cardColors(containerColor = LightBlueShade1),
        shape = MaterialTheme.shapes.medium,
        onClick = {onEvent(HomeEvent.showAddCityDialog)}
    ) {
        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add city",
                modifier = Modifier.size(48.dp),
                tint = Color.White
            )
        }
    }
}
@Composable
fun BottomBar(navController: NavController?, currentRoute: String) {
    BottomAppBar(
        //Modifier.navigationBarsPadding(),
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
