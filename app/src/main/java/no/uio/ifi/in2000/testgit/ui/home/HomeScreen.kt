@file:OptIn(ExperimentalMaterial3Api::class)

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import no.uio.ifi.in2000.testgit.data.room.City
import no.uio.ifi.in2000.testgit.data.room.SortType
import no.uio.ifi.in2000.testgit.ui.home.AddCityDialog
import no.uio.ifi.in2000.testgit.ui.home.CityEvent
import no.uio.ifi.in2000.testgit.ui.home.HomeUiState
import no.uio.ifi.in2000.testgit.ui.home.HomeViewModel
import no.uio.ifi.in2000.testgit.ui.map.TopBar
import no.uio.ifi.in2000.testgit.ui.theme.DarkBlue
import no.uio.ifi.in2000.testgit.ui.theme.LightBlue
import no.uio.ifi.in2000.testgit.ui.theme.LightBlueShade1
import no.uio.ifi.in2000.testgit.ui.theme.White

@Composable
fun HomeScreen(navController : NavController?,
               currentRoute : String,
               homeViewModel : HomeViewModel,
               onEvent: (CityEvent) -> Unit
) {

    val homeUiState : HomeUiState by homeViewModel.homeUiState.collectAsState()

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

            Row (
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ){
                SortMenu(onEvent = onEvent)
                Button(
                    onClick = { onEvent(CityEvent.updateOrder) }
                ) {
                    if (homeUiState.descendingOrder){
                        Text(text = "DESC")
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = "Descending")
                    } else {
                        Text(text = "ASC")
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowUp,
                            contentDescription = "Descending")
                    }

                }

            }

                /*
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

             */

            LazyColumn(
                modifier = modifier,
                 horizontalAlignment = Alignment.CenterHorizontally,
                //Add scrollable function #TO_DO
            ) {
                if (homeUiState.descendingOrder){
                    items(homeUiState.citiesDesc) { city ->
                        MainCard(city, onEvent)
                    }
                } else {
                    items(homeUiState.cities) { city ->
                        MainCard(city, onEvent)
                    }
                }
            }

        }
    }
}

@Composable
fun SortMenu(
    onEvent: (CityEvent) -> Unit
){
    var expanded by remember {mutableStateOf(false)}
    val items = SortType.entries
    var selectedOption by remember { mutableStateOf(SortType.All) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = it },
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = selectedOption.name,
            onValueChange = {},
            label = { Text(text = "Sorting") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEach{ item ->
                DropdownMenuItem(
                    text = { Text(item.name) },
                    onClick = {
                        selectedOption = item
                        expanded = false
                        onEvent(CityEvent.SortCities(item))
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
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
