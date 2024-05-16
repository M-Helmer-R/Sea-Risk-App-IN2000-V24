@file:OptIn(ExperimentalPermissionsApi::class)

package no.uio.ifi.in2000.testgit.ui.home

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import no.uio.ifi.in2000.testgit.data.room.City

sealed interface HomeEvent {
    //Updating UI-state functions
    data class InsertCity(val name : String, val lon : String, val lat: String) : HomeEvent
    data class UpdateFavorite(val city : City) : HomeEvent
    data class SetUserPosition(val lon : Double, val lat: Double) : HomeEvent
    data class DeleteCity(val city: City) : HomeEvent

    //Dialog functions
    data object UpdateNearest : HomeEvent
    data object ShowPermissionDialog :  HomeEvent
    data object HidePermissionDialog : HomeEvent
    data object ShowDeniedPermissionDialog : HomeEvent
    data object HideDeniedPermissionDialog : HomeEvent
    data object ShowDisabledLocationDialog : HomeEvent
    data object HideDisabledLocationDialog : HomeEvent

}