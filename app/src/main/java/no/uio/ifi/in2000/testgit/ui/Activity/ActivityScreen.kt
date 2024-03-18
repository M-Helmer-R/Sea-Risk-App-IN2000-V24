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
fun ActivityScreen( navController: NavController, activityScreenViewModel: ActivityScreenViewModel = viewModel()){
    //activityScreenViewModel: ActivityScreenViewModel = viewModel()

    val nowCastUIState = activityScreenViewModel.nowCastUIState.collectAsState()
    val metAlertsUIState = activityScreenViewModel.metAlertsUIState.collectAsState()

    Text("${nowCastUIState.value.nowCastData?.data?.instant?.details?.airTemperature}")







}

@Composable
@Preview
fun ActivityScreenPreview(){
}