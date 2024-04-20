import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import no.uio.ifi.in2000.testgit.data.room.City
import no.uio.ifi.in2000.testgit.ui.home.CityEvent
import no.uio.ifi.in2000.testgit.data.room.SortType
import no.uio.ifi.in2000.testgit.ui.home.AddCityDialog
import no.uio.ifi.in2000.testgit.ui.home.HomeUiState
import no.uio.ifi.in2000.testgit.ui.home.HomeViewModel
import no.uio.ifi.in2000.testgit.ui.map.TopBar

val DarkBlue = Color(0xFF013749)
val LightBlue = Color(0xFF0C8891)
val White = Color(0xFFFFFFFF)

val DarkBlueShade1 = Color(0xFF012A3C)
val DarkBlueShade2 = Color(0xFF011E2F)
val DarkBlueShade3 = Color(0xFF010F17)

val LightBlueShade1 = Color(0xFF0B7780)
val LightBlueShade2 = Color(0xFF0A666F)
val LightBlueShade3 = Color(0xFF09555F)

val LightGray = Color(0xFFF5F5F5)
val MediumGray = Color(0xFFCCCCCC)
val DarkGray = Color(0xFF666666)

val Yellow = Color(0xFFFFFFD700)
val Orange = Color(0xFFFFA500)
val Green = Color(0xFF00FF00)
val Purple = Color(0xFF800080)

val Black = Color(0xFF000000)
val OffWhite = Color(0xFFF9F9F9)
val Beige = Color(0xFFF5F5DC)
val Brown = Color(0xFF8B4513)

@Composable
fun HomeScreen(navController : NavController?,
               currentRoute : String,
               homeViewModel : HomeViewModel,
               onEvent: (CityEvent) -> Unit
) {

    val homeUiState : HomeUiState by homeViewModel.cityUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
    ) {

        TopBar()

        HorizontalContent(homeUiState.preloaded)

        MainContent(
            homeUiState = homeUiState,
            navController = navController,
            currentRoute = currentRoute,
            modifier = Modifier.weight(1f),
            onEvent = onEvent,
        )
    }
}

//Custom
@Composable
fun MainContent(homeUiState : HomeUiState,
                navController : NavController?,
                currentRoute : String,
                modifier: Modifier = Modifier,
                onEvent: (CityEvent) -> Unit,
            ) {
    Scaffold (
        containerColor = Color.Transparent,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(CityEvent.showDialog)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add contact"
                )
            }
        },
        bottomBar = {BottomBar(navController = navController, currentRoute = currentRoute)}
    ) { padding ->

        if (homeUiState.isAddingCity) {
            AddCityDialog(onEvent = onEvent)
        }
        Column (modifier = Modifier.fillMaxSize()){
            Text(
                text = "Dine byer",
                style = MaterialTheme.typography.headlineSmall.copy(color = White),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            /*
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SortType.entries.forEach { sortType ->
                    Row(
                        modifier = Modifier.clickable {
                            onEvent(CityEvent.SortCities(sortType))
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = homeUiState.sortType == sortType,
                            onClick = {
                                onEvent(CityEvent.SortCities(sortType))
                            }
                        )
                        Text(text = sortType.name,
                            style = MaterialTheme.typography.titleSmall.copy(color = White)
                        )
                    }
                }
            }

             */
            Row {
                SortType.entries.forEach { sortType ->
                    Row(
                        modifier = Modifier.clickable {
                            onEvent(CityEvent.SortCities(sortType))
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = homeUiState.sortType == sortType,
                            onClick = {
                                onEvent(CityEvent.SortCities(sortType))
                            }
                        )
                        Text(
                            text = sortType.name,
                            style = MaterialTheme.typography.titleSmall.copy(color = White)
                        )
                    }
                }
            }
            LazyColumn(
                modifier = modifier,
                 horizontalAlignment = Alignment.CenterHorizontally,
                //Add scrollable function #TO_DO
            ) {
                /*
                item {
                    SortType.entries.forEach { sortType ->
                        Row(
                            modifier = Modifier.clickable {
                                onEvent(CityEvent.SortCities(sortType))
                            },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = homeUiState.sortType == sortType,
                                onClick = {
                                    onEvent(CityEvent.SortCities(sortType))
                                }
                            )
                            Text(text = sortType.name,
                                style = MaterialTheme.typography.titleSmall.copy(color = White)
                            )
                        }
                    }
                }

                 */
                items(homeUiState.cities) { city ->
                    MainCard(city, onEvent)
                }
            }
        }
    }
}
@Composable
fun HorizontalContent(preloaded : List<City>){
    Text(
        text = "Nærmeste aktivitetsplasser:",
        style = MaterialTheme.typography.headlineSmall.copy(color = White),
        modifier = Modifier.padding(16.dp)
    )
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(96.dp)
    ){
        items(preloaded) { city ->
            HorizontalCard(city)
        }
    }
}

@Composable
fun MainCard(
    city: City,
    onEvent: (CityEvent) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            //.size(width = 140.dp, height = 90.dp)
            .padding(12.dp),
        colors = when (city.favorite) {
            1 -> CardDefaults.cardColors(containerColor = LightBlueShade1)
            else -> CardDefaults.cardColors(containerColor = LightBlue)
        },
        shape = MaterialTheme.shapes.medium
    ) {
        Row (
            modifier = Modifier
                .padding(6.dp)
                .background(Color.Transparent)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .background(Color.Transparent)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Row (

                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Text(
                        modifier = Modifier.padding(2.dp),
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

                //Text(text = city.distance, style = MaterialTheme.typography.bodySmall.copy(color = White))
                Row (modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ){

                    Row(
                        modifier = Modifier.padding(2.dp)
                    ){
                        Text(text = "Lat: ",
                            style = MaterialTheme.typography.titleSmall.copy(color = White)
                        )
                        Text(text = city.lat.toString())
                    }

                    Row(
                        modifier = Modifier.padding(2.dp)
                    ) {
                        Text(
                            text = "Lon: ",
                            style = MaterialTheme.typography.titleSmall.copy(color = White)
                        )
                        Text( text = city.lat.toString(),)
                    }
                }
            }

            Column (
                modifier = Modifier
                    .padding(4.dp)
                    .background(Color.Transparent),
                    //.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
            ){
                Button(
                    onClick = {
                        Log.w("SCREEN", "City: ${city.favorite}")
                        onEvent(CityEvent.updateFavorite(city))
                        Log.w("SCREEN", "City: ${city.favorite}")
                    }
                ) {
                    if (city.favorite == 1) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "is favorite",
                            tint = Color.Yellow
                        )
                        Log.w("CITY_SCREEN", "is favorite")
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = "not favorite",
                        )
                        Log.w("CITY_SCREEN", "is not favorite")
                    }
                }

                if (city.customized == 1) {
                    Button(
                        onClick = {
                            onEvent(CityEvent.DeleteCity(city))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HorizontalCard(city: City) {
    Card(
        modifier = Modifier
            .height(90.dp)
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = LightBlue),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight()
            ) {
                Text(text = city.name,
                    style = MaterialTheme.typography.titleMedium.copy(color = White)
                )

                Text(text = "10 km",
                    style = MaterialTheme.typography.bodySmall.copy(color = White)
                )
            }
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
