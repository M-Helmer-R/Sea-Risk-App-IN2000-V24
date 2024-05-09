package no.uio.ifi.in2000.testgit.ui.home

import com.google.accompanist.permissions.rememberPermissionState
import no.uio.ifi.in2000.testgit.data.room.City

data class HomeUiState(

    //Main
    val allCities : List<City> = emptyList(),
    val favorites : List<City> = emptyList(),
    val preloaded : List<City> = emptyList(),
    val nearestCities : Map<City, Double> = emptyMap<City, Double>(),

    //AddCityDialog
    val cityName: String = "",
    val cityLon: String = "",
    val cityLat: String = "",
    val fave: Int = 1,
    val custom : Int = 1,

    val nameError : Boolean = false,
    val lonError : Boolean = false,
    val latError : Boolean = false,
    val errorMessage : String = "Not valid input",

    val isAddingCity: Boolean = false,

    //LocationDialog
    val locationDialog: Boolean = false,
    val permissionDialog : Boolean = false,
    val manualLocationDialog : Boolean = false,

    //Positon dialogs
    var userLat : Double = 59.56374,
    var userLon : Double = 10.43067,


    //Add city dialog

    )

/*
fun getNearestCities(cities : List<City>, userLat: Double, userLon : Double) : Map<City, Double> {

    val citiesDist : MutableMap<City, Double> = mutableMapOf<City, Double>()

    for (city in cities) {
        citiesDist.put(city, haversine(city.lat, city.lon, userLat, userLon))
    }

    return citiesDist.toList().sortedBy { it.second }.toMap()
}

 */
