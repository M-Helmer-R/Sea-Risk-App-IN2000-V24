package no.uio.ifi.in2000.testgit.ui.home

import no.uio.ifi.in2000.testgit.data.room.City
import no.uio.ifi.in2000.testgit.data.room.SortType

data class HomeUiState(
    //DTO

    //Main
    val sortType: SortType = SortType.All,
    val allCities : List<City> = emptyList(),
    val favorites : List<City> = emptyList(),
    val preloaded : List<City> = emptyList(),
    val nearestCities : Map<City, Double> = emptyMap<City, Double>(),
    val descendingOrder : Boolean = true,

    val isAddingCity: Boolean = false,
    val deniedPermission: Boolean = false,
    val askingPermission: Boolean = false,

    val rememberPermission : Boolean = false,
    val locationPermission : Boolean = false,

    //Positon dialogs
    var userLat : Double = 59.56374,
    var userLon : Double = 10.43067,

    //Add city dialog
    val cityName: String = "",
    val cityLon: Double = - 1.0,
    val cityLat: Double = - 1.0,
    val fave: Int = 1,
    val custom : Int = 1,
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
