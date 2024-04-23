package no.uio.ifi.in2000.testgit.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.testgit.data.room.City
import no.uio.ifi.in2000.testgit.data.room.SortType
import no.uio.ifi.in2000.testgit.data.room.haversine

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel (
    private val dao : CityDao
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.Favorites)
    private val _preloaded = dao.getPreloaded()
    //private val _cities = dao.getFavourites()

    private val _cities = _sortType.flatMapLatest { it ->
        when (it) {
            SortType.All -> dao.getAll()
            SortType.Favorites -> dao.getFavourites()
            SortType.Customs -> dao.getCustoms()
            SortType.Preloaded -> dao.getPreloaded()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())



    private val _homeUiState = MutableStateFlow(HomeUiState())

    val homeUiState = combine(_homeUiState, _sortType, _cities, _preloaded,
    ) { state, sortType, cities, preloaded ->
        state.copy(
            cities = cities,
            sortType = sortType,
            preloaded = getNearestCities(preloaded, state.userLon, state.userLat),
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())

    fun onEvent( event : HomeEvent){
        when (event) {

            is HomeEvent.DeleteHome -> {
                viewModelScope.launch {dao.deleteCity(event.city)
                }
            }

            is HomeEvent.SortCities -> {
                _sortType.value = event.sortType
            }

            HomeEvent.hideAddCityDialog -> {
                _homeUiState.update { it.copy( isAddingCity = false
                ) }
            }

            HomeEvent.updateOrder -> {
                _homeUiState.update { it.copy( descendingOrder = !it.descendingOrder)
                }
            }

            HomeEvent.saveCity -> {
                val name = homeUiState.value.cityName
                val lat = homeUiState.value.cityLat
                val lon = homeUiState.value.cityLon

                Log.w("VIEW_MODEL", "Name: $name, Lat: $lat, Lon: $lon" )

                if (name.isBlank() || (lat == -1.0 && lon == -1.0)){
                    return
                }

                val newCity = City(name = name, lat = lat, lon = lon, customized = 1)

                //Sjekk her
                viewModelScope.launch {
                    dao.upsertCity(newCity)
                    Log.w("VIEW_MODEL", "City: ${newCity.lat}" )
                }

                _homeUiState.update { it.copy(
                    isAddingCity = false,
                    cityName = "",
                    cityLon = -1.0,
                    cityLat = -1.0,
                ) }
            }

            is HomeEvent.updateFavorite -> {
                viewModelScope.launch(Dispatchers.IO){
                    if (event.city.favorite == 1) {
                        dao.removeFavoriteByID(event.city.cityId)

                       // Log.w("VIEW_MODEL", "City: ${city.lat}" )
                    } else {
                        dao.setFavoriteByID(event.city.cityId)
                    }
                }
            }

            is HomeEvent.setName -> {
                _homeUiState.update { it.copy(
                    cityName = event.name
                ) }

            }

            HomeEvent.showAddCityDialog -> {
                _homeUiState.update { it.copy( isAddingCity = true
                ) }
            }

            is HomeEvent.setCityPosition -> {
                _homeUiState.update { it.copy(
                    cityLat = event.lat,
                    cityLon = event.lon
                ) }
            }

            is HomeEvent.setUserPosition ->
                _homeUiState.update { it.copy(
                    cityLon = event.lon,
                    cityLat = event.lat,
                ) }

            HomeEvent.updatePreloaded ->
                _homeUiState.update {it.copy(
                    preloaded = getNearestCities(
                    it.preloaded.keys.toList(),
                    it.userLat,
                    it.userLon
                    ),
                    isChangingPosition = false,
                    ) }

            HomeEvent.hidePositionDialog ->
                _homeUiState.update { it.copy( isChangingPosition = false
            ) }

            HomeEvent.showPositionDialog ->
                _homeUiState.update { it.copy( isChangingPosition = true
            ) }
        }
    }
}

//Fiks denne
fun getNearestCities(cities : List<City>, lon : Double, lat : Double) : Map<City, Double> {

    val citiesDist : MutableMap<City, Double> = mutableMapOf<City, Double>()

    for (city in cities) {
        citiesDist.put(city, haversine(city.lat, city.lon, lat, lon))
    }

    return citiesDist.toList().sortedBy { it.second }.toMap()
}