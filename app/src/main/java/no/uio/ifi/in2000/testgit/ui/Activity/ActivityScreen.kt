package no.uio.ifi.in2000.testgit.ui.Activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import no.uio.ifi.in2000.testgit.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityScreen(chosenCity: String, navController: NavController, activityScreenViewModel: ActivityScreenViewModel = viewModel()){

    val nowCastUIState = activityScreenViewModel.nowCastUIState.collectAsState()
    val metAlertsUIState = activityScreenViewModel.metAlertsUIState.collectAsState()
    val activities = listOf("sailing", "surfing", "swimming", "kayaking")
    var selectedButton by remember { mutableStateOf(activities[0]) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "${chosenCity}") },
                navigationIcon = {
                    ElevatedButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "arrow to HomeScreen"
                        )
                    }
                }
            )
        }, // end of topbar

        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly

                ) {
                    // sailing button
                    ElevatedButton(
                        onClick = { selectedButton = "sailing" },

                    ) {
                        Icon(
                            painter = painterResource(R.drawable.sailboaticon),
                            contentDescription = "sailboat"
                        )
                    }

                    // surfing button
                    ElevatedButton(
                        onClick = { selectedButton = "surfing" }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.surfingicon),
                            contentDescription = "sailboat"
                        )

                    }

                    // swimming button
                    ElevatedButton(
                        onClick = { selectedButton = "swimming" },
                        shape = CircleShape,

                    ) {
                        Icon(
                            painter = painterResource(R.drawable.swimmingicon),
                            contentDescription = "sailboat"
                        )

                    }

                    // kayak button
                    ElevatedButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(R.drawable.kayakicon),
                            contentDescription = "sailboat"
                        )

                    }
                }//end of row
            }// end of bottomappbar
        },// end of scaffold itom bottomBar
        content = {
            Column(
                modifier = Modifier.padding(it)
            ){
                if(metAlertsUIState.value.metAlertsData != null) {
                    Text("VARSLER:" +
                            "\nType: ${metAlertsUIState.value.metAlertsData!!.alertProperties?.awarenessType}" +
                            "\nFarenivå: ${metAlertsUIState.value.metAlertsData!!.alertProperties?.awarenessSeriousness}" +
                            "\nBeskrivelse: ${metAlertsUIState.value.metAlertsData!!.alertProperties?.consequences}" +
                            "\nAnbefaling: ${metAlertsUIState.value.metAlertsData!!.alertProperties?.awarenessResponse}")

                } else {
                    Text("VARSLER: Ingen varsler")
                }
                Spacer(modifier = Modifier.padding(it))
                Text("VÆRINFORMASJON" +
                        "\nTemperatur: ${nowCastUIState.value.nowCastData?.data?.instant?.details?.airTemperature}" +
                        "\nVind hastighet: ${nowCastUIState.value.nowCastData?.data?.instant?.details?.windSpeed}" +
                        "\nVindkast: ${nowCastUIState.value.nowCastData?.data?.instant?.details?.windSpeedOfGust}" +
                        "\nReinmengde (mm): ${nowCastUIState.value.nowCastData?.data?.next1Hours?.details?.precipitationAmount}" +
                        "\nBeskrivelse: ${nowCastUIState.value.nowCastData?.data?.next1Hours?.summary?.symbolCode}")

            }
        },
    )// end of scaffold
}

@Composable
@Preview
fun ActivityScreenPreview(){
}