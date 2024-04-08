package no.uio.ifi.in2000.testgit.data.room

sealed interface CityEvent {
    object saveCity : CityEvent
    data class setName(val name : String): CityEvent
    data class setFavorite(val fave : Boolean) : CityEvent

    object show

    //16.30

}