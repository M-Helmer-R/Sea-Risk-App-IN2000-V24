package no.uio.ifi.in2000.testgit.ui

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import no.uio.ifi.in2000.testgit.data.room.City
import no.uio.ifi.in2000.testgit.data.room.CityEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCityDialog(
    state: CityUiState,
    onEvent: (CityEvent) -> Unit,
    modifier: Modifier = Modifier,
){
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onEvent(CityEvent.hideDialog) },
        confirmButton = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd,
                        ){
                            Button(
                                onClick = { CityEvent.saveCity}
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

                TextField(
                    value = state.name,
                    onValueChange = {
                        onEvent(CityEvent.setName(it))
                    },
                    leadingIcon = {
                                  Icon(
                                      imageVector = Icons.Filled.LocationOn,
                                      contentDescription = "Pin")
                    },
                    /*
                    keyboardActions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next

                    ),
                     */

                    label = {
                        Text(text = "Enter the city")
                            },
                    placeholder = {
                        Text(text = "City")
                    }

                )

                TextField(
                    value = state.lat.toString(),
                    onValueChange = { it ->
                        if (it.toDoubleOrNull() != null) {
                            error("Not valid")
                        } else {
                            onEvent(CityEvent.setLat(it.toDouble()))
                        }
                    },
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Right),

                    /*
                    keyboardActions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                     */
                    label = {
                        Text(text = "Enter the latitude")
                    },
                    placeholder = {
                        Text(text = "latitude")
                    },
                    suffix = {
                        Text(text = "º")
                    }
                )
            }
        }
    )
}