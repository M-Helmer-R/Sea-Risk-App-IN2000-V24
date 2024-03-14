package no.uio.ifi.in2000.testgit.ui.Activity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState


@Composable
fun ActivityScreen(activityScreenViewModel: ActivityScreenViewModel = ActivityScreenViewModel()){
    val nowCastUIState = activityScreenViewModel.nowCastUIState.collectAsState()



}