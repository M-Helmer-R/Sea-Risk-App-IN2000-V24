package no.uio.ifi.in2000.testgit.ui.Activity

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun ActivityScreen(lat: String, lon: String, navController: NavController){
    //activityScreenViewModel: ActivityScreenViewModel = viewModel()
    /*
    val nowCastUIState = activityScreenViewModel.nowCastUIState.collectAsState()
    val metAlertsUIState = activityScreenViewModel.metAlertsUIState.collectAsState()

     */



    Text("Lat: $lat, Lon: $lon")



}

@Composable
@Preview
fun ActivityScreenPreview(){
    ActivityScreen("59", "10", navController = rememberNavController())
}