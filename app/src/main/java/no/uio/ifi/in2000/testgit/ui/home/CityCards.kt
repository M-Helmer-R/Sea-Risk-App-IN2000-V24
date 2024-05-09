package no.uio.ifi.in2000.testgit.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import no.uio.ifi.in2000.testgit.data.room.City
import no.uio.ifi.in2000.testgit.ui.theme.LightBlue
import no.uio.ifi.in2000.testgit.ui.theme.LightBlueShade1
import no.uio.ifi.in2000.testgit.ui.theme.White

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