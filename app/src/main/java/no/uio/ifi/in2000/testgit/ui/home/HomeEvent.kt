package no.uio.ifi.in2000.testgit.ui.home

import no.uio.ifi.in2000.testgit.data.room.City

sealed interface HomeEvent {
    //DTO
    data class insertCity(val name : String, val lon : String, val lat: String) : HomeEvent
    data class setName(val name : String): HomeEvent
    data class setCityLat(val lat : String) : HomeEvent
    data class setCityLon(val lon : String) : HomeEvent
    data class updateFavorite(val city : City) : HomeEvent
    data class setUserPosition(val lon : Double, val lat: Double) : HomeEvent
    data class DeleteHome(val city: City) : HomeEvent
    data class OpenActivity(val city : City) : HomeEvent
    //DialogError

    object setNameError : HomeEvent
    object setLonError : HomeEvent
    object setLatError : HomeEvent
    object updateNearest : HomeEvent
    object showAddCityDialog: HomeEvent
    object hideAddCityDialog: HomeEvent
    object showLocationDialog :  HomeEvent
    object hideLocationDialog : HomeEvent
    object showManualLocationDialog :  HomeEvent
    object hideManualLocationDialog : HomeEvent
    object showPermissionDialog :  HomeEvent
    object hidePermissionDialog : HomeEvent

}