package no.uio.ifi.in2000.testgit.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.testgit.data.room.City
import no.uio.ifi.in2000.testgit.data.room.CityDao
import no.uio.ifi.in2000.testgit.data.room.CityEvent
import no.uio.ifi.in2000.testgit.data.room.SortType

data class CityUiState(
    val cities : List<City> = emptyList(),
    val name : String = "",
    val lon : String = "",
    val lat : String = "",
    val fave : String = "",
    val isAddingCity : Boolean = false,
    val sortType: SortType = SortType.NAME

)
class CityViewModel (
    private val dao : CityDao
) : ViewModel() {
    private val _sortType = MutableStateFlow(SortType.NAME)
    private val _state = MutableStateFlow(CityUiState())

    fun onEvent( event : CityEvent){
        when (event) {
            is CityEvent.DeleteCity -> {
                viewModelScope.launch {dao.deleteCity(event.city)
                }
            }
            is CityEvent.SortCitites -> {
                TODO()
            }
            CityEvent.hideDialog -> {
                _state.update { it.copy( isAddingCity = false
                ) }
            }

            is CityEvent.removeFavorite -> TODO()
            CityEvent.saveCity -> TODO()
            is CityEvent.setFavorite -> {
                //viewModelScope.launch {dao.s(event.city)
                _state.update { it.copy(
                    name = event.name
                ) }
            }
            is CityEvent.setName -> {
                _state.update { it.copy(
                    name = event.name
                ) }

            }
            CityEvent.showDialog -> TODO()
        }

    /*
    fun onEvent( event : CityEvent){
        when (event) {
            is CityEvent.DeleteCity -> {viewModelScope.launch {dao.deleteCity(event.city)}
            is CityEvent.SortCitites -> TODO()
            CityEvent.hideDialog -> {
                _state.update { it.copy(isAddingCity = false) }
                }
            CityEvent.saveCity -> {

                }
            is CityEvent.setFavorite -> > {
                    _state.update{ it.copy(
                        fave = true )}
                }
            is CityEvent.setName -> {
                _state.update{ it.copy(
                    name = event.name )}
                }
            CityEvent.showDialog -> TODO()
        }
    }

     */
}