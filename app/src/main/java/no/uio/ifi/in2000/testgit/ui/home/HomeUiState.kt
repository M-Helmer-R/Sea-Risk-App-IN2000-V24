package no.uio.ifi.in2000.testgit.ui.home

import no.uio.ifi.in2000.testgit.data.room.City

data class HomeUiState(

    //Cities from database
    val allCities : List<City> = emptyList(),
    val favorites : List<City> = emptyList(),
    val preloaded : List<City> = emptyList(),
    val nearestCities : Map<City, Double> = emptyMap(),

    //Location dialogs
    val permissionDialog : Boolean = false,
    val deniedLocationDialog : Boolean = false,
    val disabledLocationDialog : Boolean = false,

    //User position
    var userLat : Double = 59.9139,
    var userLon : Double = 10.7522,

    )
