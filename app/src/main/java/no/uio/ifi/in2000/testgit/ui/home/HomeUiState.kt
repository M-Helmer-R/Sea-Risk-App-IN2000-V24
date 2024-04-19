package no.uio.ifi.in2000.testgit.ui.home

import no.uio.ifi.in2000.testgit.data.room.City
import no.uio.ifi.in2000.testgit.data.room.SortType

data class HomeUiState(
    //DTO
    val cities: List<City> = emptyList(),
    val name: String = "",
    val lon: Double = - 1.0,
    val lat: Double = - 1.0,
    val fave: Int = 0,
    val custom : Int = 1,
    val isAddingCity: Boolean = false,
    val sortType: SortType = SortType.All
)
