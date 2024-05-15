@file:OptIn(ExperimentalPermissionsApi::class)

package no.uio.ifi.in2000.testgit.ui.home

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import no.uio.ifi.in2000.testgit.data.room.City

sealed interface HomeEvent {
    //DTO
    data class InsertCity(val name : String, val lon : String, val lat: String) : HomeEvent
    data class SetName(val name : String): HomeEvent
    data class SetCityLat(val lat : String) : HomeEvent
    data class SetCityLon(val lon : String) : HomeEvent
    data class UpdateFavorite(val city : City) : HomeEvent
    data class SetUserPosition(val lon : Double, val lat: Double) : HomeEvent
    data class DeleteHome(val city: City) : HomeEvent
    //DialogError
    data class RequestLocationPermission(val locationState: MultiplePermissionsState) : HomeEvent
    data object SetNameError : HomeEvent
    data object SetLonError : HomeEvent
    data object SetLatError : HomeEvent
    data object UpdateNearest : HomeEvent
    data object ShowAddCityDialog: HomeEvent
    data object HideAddCityDialog: HomeEvent
    data object ShowPermissionDialog :  HomeEvent
    data object HidePermissionDialog : HomeEvent
    data object ShowDeniedPermissionDialog : HomeEvent
    data object HideDeniedPermissionDialog : HomeEvent
    data object ShowDisabledLocationDialog : HomeEvent
    data object HideDisabledLocationDialog : HomeEvent

}