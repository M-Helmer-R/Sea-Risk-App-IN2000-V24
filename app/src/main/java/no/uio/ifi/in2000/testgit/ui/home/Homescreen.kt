package no.uio.ifi.in2000.testgit.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import no.uio.ifi.in2000.testgit.model.CityDatabase.CityDatabase
import no.uio.ifi.in2000.testgit.ui.Activity.ActivityScreen
import no.uio.ifi.in2000.testgit.ui.Activity.ActivityScreenViewModel

@Composable
fun Homescreen(navController: NavController, homescreenViewModel: HomescreenViewModel = viewModel()){
    Column(


    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 200.dp),
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()


        ){

            val myCities = CityDatabase.entries.toTypedArray()
            items(myCities){cities ->
                Button(onClick = {
                    navController.navigate("ActivityScreen/${cities.cityName}")

                }) {
                    Text(cities.cityName)
                }

            }
            /*
            Button(onClick = {
                navController.navigate("ActivityScreen/Oslo")

            }) {
                Text("Oslo")
            }

            Button(onClick = {
                navController.navigate("ActivityScreen/Bergen")
            }) {
                Text("Bergen")
            }

             */


        }
    }

}