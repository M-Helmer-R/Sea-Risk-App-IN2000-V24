package no.uio.ifi.in2000.testgit.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.testgit.data.room.City
import no.uio.ifi.in2000.testgit.data.room.CityDao
import no.uio.ifi.in2000.testgit.data.room.CityEvent
import no.uio.ifi.in2000.testgit.data.room.SortType

@OptIn(ExperimentalCoroutinesApi::class)
class CityViewModel (
    private val dao : CityDao
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.NAME)
    private val _cities = _sortType.flatMapLatest { it ->
        when (it) {
            SortType.NAME -> dao.getAll()
            SortType.FAVE -> dao.getFavourites()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    }
    private val _cityUiState = MutableStateFlow(CityUiState())
    //val cityUiState : StateFlow<CityUiState> = _cityUiState.asStateFlow()

    val cityUiState = combine(_cityUiState, _sortType, _cities) { state, sortType, cities ->
        state.copy(
            cities = cities,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CityUiState())

    /*
    fun loadCities()

     */
    fun onEvent( event : CityEvent){
        when (event) {

            is CityEvent.DeleteCity -> {
                viewModelScope.launch {dao.deleteCity(event.city)
                }
            }

            is CityEvent.SortCities -> {
                _sortType.value = event.sortType
            }

            CityEvent.hideDialog -> {
                _cityUiState.update { it.copy( isAddingCity = false
                ) }
            }

            CityEvent.saveCity -> {
                val name = cityUiState.value.name
                val lat = cityUiState.value.lat
                val lon = cityUiState.value.lon

                if (name.isBlank()){
                    return
                }

                val newCity = City(name = name, lat = lat, lon = lon)

                viewModelScope.launch {
                    dao.upsertCity(newCity)
                }

                _cityUiState.update { it.copy(
                    isAddingCity = false,
                    name = "",
                    lon = 0.0,
                    lat =0.0,
                ) }
            }

            is CityEvent.setFavorite -> {
                //viewModelScope.launch {dao.s(event.city)
                _cityUiState.update { it.copy(
                    fave = true
                ) }
            }

            is CityEvent.setName -> {
                _cityUiState.update { it.copy(
                    name = event.name
                ) }

            }
            CityEvent.showDialog -> {
                _cityUiState.update { it.copy( isAddingCity = true
                ) }
            }

            is CityEvent.removeFavorite -> {
                _cityUiState.update { it.copy(
                    fave = false
                ) }
            }
            is CityEvent.setLat -> {
                _cityUiState.update { it.copy(
                    lat = event.lat
                ) }
            }
            is CityEvent.setLon ->  {
                _cityUiState.update { it.copy(
                    lon = event.lon
                ) }
            }
        }
    }
}