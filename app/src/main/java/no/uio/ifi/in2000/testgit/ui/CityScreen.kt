@file:OptIn(ExperimentalMaterial3Api::class)

package no.uio.ifi.in2000.testgit.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import no.uio.ifi.in2000.testgit.data.room.City
import no.uio.ifi.in2000.testgit.data.room.CityEvent
import no.uio.ifi.in2000.testgit.data.room.SortType

@Composable
fun CityScreen(
    cityViewModel: CityViewModel,
    onEvent: (CityEvent) -> Unit
) {
    val cityUiState : CityUiState by cityViewModel.cityUiState.collectAsState()

    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(CityEvent.showDialog)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add contact"
                )
            }

        }
    ){ padding ->
        if (cityUiState.isAddingCity){
            AddCityDialog(onEvent = onEvent)
        }
        LazyColumn(contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                Row (
                    modifier = Modifier
                        .fillMaxSize(),
                        //.horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    SortType.entries.forEach { sortType ->
                        Row (
                            modifier = Modifier.clickable {
                                onEvent(CityEvent.SortCities(sortType))
                            },
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            RadioButton(selected = cityUiState.sortType == sortType,
                                onClick = {
                                    onEvent(CityEvent.SortCities(sortType))
                                }
                            )
                            Text(text = sortType.name)
                        }
                    }
                }
            }

             /*

            items(cityUiState.cities.filter { city -> city.favorite }){ city ->
                Row (modifier = Modifier.fillMaxSize()
                ) {
                    Column {
                        CityCard(city = city, onEvent)
                    }
                }
            }


              */
            items(cityUiState.cities){ city ->
                Row (modifier = Modifier.fillMaxSize()
                ) {
                    Column {
                        CityCard(city = city, onEvent)
                    }
                }
            }
        }
    }
}

@Composable
fun CityCard(
    city : City,
    onEvent: (CityEvent) -> Unit,
) {
    Card (
        modifier = Modifier.fillMaxWidth(),
        /*
        colors = CardDefaults.cardColors(
            containerColor = when (city.customized) {
                1 -> MaterialTheme.colorScheme.surfaceVariant
                else -> MaterialTheme.colorScheme.Gray
            },
        )

         */
    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = city.name)
            Text(text = city.lat.toString())
            Text(text = city.lon.toString())
            if (city.customized == 1) {
                Icon(imageVector = Icons.Filled.Build, contentDescription ="Custom",
                    modifier = Modifier.fillMaxHeight())
            }
            Button(
                onClick = {
                    Log.w("SCREEN", "City: ${city.favorite}" )
                    onEvent(CityEvent.updateFavorite(city))
                    Log.w("SCREEN", "City: ${city.favorite}" )
                }
            ) {
                if (city.favorite == 1) {
                    Icon(imageVector = Icons.Filled.Star,
                        contentDescription = "is favorite",
                        tint = Color.Yellow
                    )
                    Log.w("CITY_SCREEN", "is favorite")
                } else {
                    Icon(imageVector = Icons.Outlined.Star,
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
                    Icon(imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }
        }
    }


}

//@Preview

/*
@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Preview - Citites"
)

@Composable
fun GreetingPreview() {
    TestGitTheme {
        val city = City(0, "Oslo", 70.0, 60.0, true)
       CityCard(city = city, onEvent = viewModel::onEvent )
    }
}


 */

