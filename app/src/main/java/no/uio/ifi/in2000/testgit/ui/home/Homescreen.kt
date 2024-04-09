package no.uio.ifi.in2000.testgit.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

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