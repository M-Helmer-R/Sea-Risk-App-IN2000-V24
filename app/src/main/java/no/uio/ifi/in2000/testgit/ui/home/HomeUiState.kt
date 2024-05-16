package no.uio.ifi.in2000.testgit.ui.home

import no.uio.ifi.in2000.testgit.data.room.City

data class HomeUiState(

    //Main
    val allCities : List<City> = emptyList(),
    val favorites : List<City> = emptyList(),
    val preloaded : List<City> = emptyList(),
    val nearestCities : Map<City, Double> = emptyMap(),

    //LocationDialog
    val permissionDialog : Boolean = false,
    val deniedLocationDialog : Boolean = false,
    val disabledLocationDialog : Boolean = false,

    //Positon dialogs
    var userLat : Double = 59.56374,
    var userLon : Double = 10.43067,

    )
