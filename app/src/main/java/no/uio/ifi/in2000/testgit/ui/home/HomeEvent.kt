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
    object setNameError : HomeEvent
    object setLonError : HomeEvent
    object setLatError : HomeEvent
    //DialogError
    object updateNearest : HomeEvent
    object showAddCityDialog: HomeEvent
    object hideAddCityDialog: HomeEvent
    object showDeniedPermissionDialog :  HomeEvent
    object hideDeniedPermissionDialog : HomeEvent
    object showPermissionDialog :  HomeEvent
    object hidePermissionDialog : HomeEvent


}