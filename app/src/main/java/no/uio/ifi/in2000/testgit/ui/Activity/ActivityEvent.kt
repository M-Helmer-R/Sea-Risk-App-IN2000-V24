package no.uio.ifi.in2000.testgit.ui.Activity

sealed interface ActivityEvent {
    data class AddFavorite(val name : String, val lat : String, val lon : String) : ActivityEvent
    data class RemoveFavorite(val name: String) : ActivityEvent
    data class CheckFavorite(val name: String) : ActivityEvent
}