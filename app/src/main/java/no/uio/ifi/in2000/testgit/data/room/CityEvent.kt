package no.uio.ifi.in2000.testgit.data.room

sealed interface CityEvent {
    object saveCity : CityEvent
    data class setName(val name : String): CityEvent
    data class setFavorite(val fave : Boolean) : CityEvent
    data class removeFavorite(val fave : Boolean) : CityEvent
    object showDialog: CityEvent
    object hideDialog: CityEvent

    data class SortCitites(val sortType : SortType) : CityEvent
    data class  DeleteCity(val city: City) : CityEvent

}