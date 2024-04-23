package no.uio.ifi.in2000.testgit.ui.home

import no.uio.ifi.in2000.testgit.data.room.City
import no.uio.ifi.in2000.testgit.data.room.SortType
import no.uio.ifi.in2000.testgit.data.room.haversine

data class HomeUiState(
    //DTO

    //Main
    val cities: List<City> = emptyList(),
    val preloaded : Map<City, Double> = emptyMap(),
    val descendingOrder : Boolean = true,

    val isAddingCity: Boolean = false,
    val isChangingPosition: Boolean = false,
    val sortType: SortType = SortType.Favorites,

    //Positon dialogs
    var userLat : Double = 0.0,
    var userLon : Double = 0.0,

    //Add city dialog
    val cityName: String = "",
    val cityLon: Double = - 1.0,
    val cityLat: Double = - 1.0,
    val fave: Int = 0,
    val custom : Int = 1,

)