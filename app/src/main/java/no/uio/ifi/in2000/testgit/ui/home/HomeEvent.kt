package no.uio.ifi.in2000.testgit.ui.home

import no.uio.ifi.in2000.testgit.data.room.City
import no.uio.ifi.in2000.testgit.data.room.SortType

sealed interface HomeEvent {
    //DTO
    object saveCity : HomeEvent
    object updateNearest : HomeEvent
    object updateOrder : HomeEvent
    object showAddCityDialog: HomeEvent
    object hideAddCityDialog: HomeEvent
    object showPositionDialog :  HomeEvent
    object hidePositionDialog : HomeEvent
    data class setName(val name : String): HomeEvent
    data class setCityPosition(val lat : Double, val lon : Double) : HomeEvent
    data class updateFavorite(val city : City) : HomeEvent
    data class setUserPosition(val lon : Double, val lat: Double) : HomeEvent
    data class SortCities(val sortType : SortType) : HomeEvent
    data class DeleteHome(val city: City) : HomeEvent
}