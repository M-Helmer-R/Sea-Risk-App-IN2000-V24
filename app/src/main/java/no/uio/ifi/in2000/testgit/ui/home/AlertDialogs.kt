package no.uio.ifi.in2000.testgit.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AddCityDialog(
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
){
    var name by remember { mutableStateOf("") }
    var lat by remember { mutableStateOf("") }
    var lon by remember { mutableStateOf("") }

    val errorMessage = "Not valid input"
    var nameError by rememberSaveable { mutableStateOf(false) }
    var latError by rememberSaveable { mutableStateOf(false) }
    var lonError by rememberSaveable { mutableStateOf(false) }

    fun validateInput(name : String, lat : String, lon : String) {
        Log.w("ADD_CITY_DIALOG", "Validating input")
        nameError = name.isEmpty()
        latError = validateCoordinates(lat)
        lonError = validateCoordinates(lon)
    }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onEvent(HomeEvent.hideAddCityDialog) },
        confirmButton = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd,
                        ){
                            Button(
                                onClick = {
                                    validateInput(name, lat, lon)
                                    Log.w("ADD_CITY_DIALOG", "Button pressed")
                                    if (!nameError && !latError && !lonError){
                                        Log.w("Add_City", "Latitude: $lat , ${lat.toDouble()}, Longitude: $lon , ${lon.toDouble()}")
                                        onEvent(HomeEvent.setName(name))
                                        onEvent(HomeEvent.setCityPosition(lat.toDouble(), lon.toDouble()))
                                        onEvent(HomeEvent.saveCity)
                                    }
                                }
                            ) {
                                Text(text = "Add")
                            }
                        }
        },
        title = { Text(text = "Add City")},
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                NameField(
                    title = "Name",
                    placeholder = "Enter name",
                    text = name,
                    onValueChange = {name = it},
                    error = nameError,
                    errorMessage = errorMessage
                )

                DoubleTextField(
                    title = "Latitude",
                    placeholder = "Enter the latitude",
                    text = lat,
                    onValueChange = {lat = it},
                    error = latError,
                    errorMessage = errorMessage

                )
                DoubleTextField(
                    title = "Longitude",
                    placeholder = "Enter the longitude",
                    text = lon,
                    onValueChange = {lon = it},
                    error = lonError,
                    errorMessage = errorMessage
                )
            }


        }
    )
}
@Composable
fun ChangePositionDialog(
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
){

    var lat by remember { mutableStateOf("") }
    var lon by remember { mutableStateOf("") }

    val errorMessage = "Not valid input"
    var latError by rememberSaveable { mutableStateOf(false) }
    var lonError by rememberSaveable { mutableStateOf(false) }

    fun validateInput(lat : String, lon : String) {
        Log.w("ADD_CITY_DIALOG", "Validating input")
        latError = validateCoordinates(lat)
        lonError = validateCoordinates(lon)
    }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(HomeEvent.hidePositionDialog)
        },

        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd,
            ){
                Button(
                    onClick = {
                        validateInput(lat, lon)
                        Log.w("ADD_CITY_DIALOG", "Button pressed")
                        if ( !latError && !lonError){
                            Log.w("Add_City", "Latitude: $lat , ${lat.toDouble()}, Longitude: $lon , ${lon.toDouble()}")
                            onEvent(HomeEvent.setUserPosition
                                (lat.toDouble(),
                                lon.toDouble())
                            )
                            onEvent(HomeEvent.updatePreloaded)
                        }
                    }
                ) {
                    Text(text = "Change")
                }
            }
        },
        title = { Text(text = "Change position")},
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                DoubleTextField(
                    title = "Latitude",
                    placeholder = "Enter the latitude",
                    text = lat,
                    onValueChange = {lat = it},
                    error = latError,
                    errorMessage = errorMessage

                )
                DoubleTextField(
                    title = "Longitude",
                    placeholder = "Enter the longitude",
                    text = lon,
                    onValueChange = {lon = it},
                    error = lonError,
                    errorMessage = errorMessage
                )
            }
        }
    )
}

@Composable
fun NameField(
    title: String,
    placeholder: String,
    text: String,
    onValueChange: (String) -> Unit,
    error : Boolean,
    errorMessage : String,
) {
    TextField(
        value = text,
        singleLine = true,
        onValueChange = onValueChange,
        label = {
            Text(text = title)
        },
        placeholder = {
            Text(text = placeholder)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Sentences),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Pin")
        },
        isError = error,
        supportingText = {
            if (error) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            if (error)
                Icon(Icons.Filled.Clear,"error",
                    tint = MaterialTheme.colorScheme.error)
        },
    )
}

@Composable
fun DoubleTextField(
    title: String,
    placeholder: String,
    text: String,
    onValueChange: (String) -> Unit,
    error : Boolean,
    errorMessage : String,

    ) {
    TextField(
        value = text,
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Pin")
        },
        onValueChange = onValueChange,
        label = {
            Text(text = title)
        },
        placeholder = {
            Text(text = placeholder)
        },
        isError = error,
        supportingText = {
            if (error) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            if (error)
                Icon(Icons.Filled.Clear,"error",
                    tint = MaterialTheme.colorScheme.error)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )

}

fun validateCoordinates(cord : String) : Boolean {
    val _cord = cord.toDoubleOrNull()
    return when {
        _cord == null -> true
        _cord < 0.0 -> true
        _cord > 360.0 -> true
        else ->
            false
    }
}
