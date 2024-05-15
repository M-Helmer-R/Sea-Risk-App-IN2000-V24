package no.uio.ifi.in2000.testgit.ui.home.Dialog

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import no.uio.ifi.in2000.testgit.ui.home.HomeEvent
import no.uio.ifi.in2000.testgit.ui.home.HomeUiState

@Composable
fun AddCityDialog(
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState
){

    fun validateInput(name : String, lat : String, lon : String) {
        Log.w("ADD_CITY_DIALOG", "Validating input")

        if (name.isEmpty()){
            onEvent(HomeEvent.SetNameError)
        }
        if (validateCoordinates(lat)) {
            onEvent(HomeEvent.SetLatError)
        }
        if (validateCoordinates(lon)){
            onEvent(HomeEvent.SetLonError)
        }
    }

    fun hasErrors() : Boolean{
        return homeUiState.nameError && homeUiState.latError && homeUiState.lonError
    }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onEvent(HomeEvent.HideAddCityDialog) },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd,
            ){
                Button(
                    onClick = {
                        validateInput(homeUiState.cityName, homeUiState.cityLat, homeUiState.cityLon) // Bør denne flyttes?
                        Log.w("ADD_CITY_DIALOG", "Button pressed")
                        if (hasErrors()){
                            //Log.w("Add_City", "Latitude: $lat , ${lat.toDouble()}, Longitude: $lon , ${lon.toDouble()}")
                            //onEvent(HomeEvent.saveCity)
                            onEvent(HomeEvent.InsertCity(homeUiState.cityName, homeUiState.cityLat, homeUiState.cityLon))
                        }
                    }
                ) {
                    Text(text = "Add")
                }
            }
        },
        title = { Text(text = "Add City") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                NameField(
                    title = "Name",
                    placeholder = "Enter name",
                    text = homeUiState.cityName,
                    onValueChange = { onEvent(HomeEvent.SetName(it))},
                    error = homeUiState.nameError,
                    errorMessage = homeUiState.errorMessage
                )

                DoubleTextField(
                    title = "Latitude",
                    placeholder = "Enter the latitude",
                    text = homeUiState.cityLat,
                    onValueChange = {onEvent(HomeEvent.SetCityLat(it))  },
                    error = homeUiState.latError,
                    errorMessage = homeUiState.errorMessage

                )

                DoubleTextField(
                    title = "Longitude",
                    placeholder = "Enter the longitude",
                    text = homeUiState.cityLon,
                    onValueChange = {onEvent(HomeEvent.SetCityLon(it))},
                    error = homeUiState.lonError,
                    errorMessage = homeUiState.errorMessage
                )
            }
        }
    )
}