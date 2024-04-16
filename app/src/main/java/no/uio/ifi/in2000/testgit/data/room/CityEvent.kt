package no.uio.ifi.in2000.testgit.data.room

sealed interface CityEvent {
    //DTO
    object saveCity : CityEvent
    data class setName(val name : String): CityEvent
    data class setLat(val lat : Double) : CityEvent
    data class setLon(val lon : Double) : CityEvent
    data class setFavorite(val fave : Boolean) : CityEvent
    data class removeFavorite(val fave : Boolean) : CityEvent
    object showDialog: CityEvent
    object hideDialog: CityEvent

    data class SortCities(val sortType : SortType) : CityEvent
    data class DeleteCity(val city: City) : CityEvent

}