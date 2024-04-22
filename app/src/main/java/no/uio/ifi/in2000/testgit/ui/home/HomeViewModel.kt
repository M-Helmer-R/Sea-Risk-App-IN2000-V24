package no.uio.ifi.in2000.testgit.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
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

    private val _sortType = MutableStateFlow(SortType.All)
    private val _preloaded = dao.getPreloaded()

    private val _citiesDesc = _sortType.flatMapLatest { it ->
        when (it) {
            SortType.All -> dao.getAllDesc()
            SortType.Favorites -> dao.getFavouritesDesc()
            SortType.Customs -> dao.getCustomsDesc()
            SortType.Preloaded -> dao.getPreloadedDesc()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _cities = _sortType.flatMapLatest { it ->
        when (it) {
            SortType.All -> dao.getAll()
            SortType.Favorites -> dao.getFavourites()
            SortType.Customs -> dao.getCustoms()
            SortType.Preloaded -> dao.getPreloaded()
            /*
        if (_descendingOrder){
            when (it) {
                SortType.All -> dao.getAll()
                SortType.Favorites -> dao.getFavourites()
                SortType.Customs -> dao.getCustoms()
                SortType.Preloaded -> dao.getPreloaded()
            }
        } else {
            when (it) {
                SortType.All -> dao.getAll()
                SortType.Favorites -> dao.getFavourites()
                SortType.Customs -> dao.getCustoms()
                SortType.Preloaded -> dao.getPreloaded()
            }
        }

        when (it) {
            SortType.All -> if (_descendingOrder) dao.getAllDesc() else dao.getAll()
            SortType.Favorites -> if (_descendingOrder) dao.getFavouritesDesc() else dao.getFavourites()
            SortType.Customs -> if (_descendingOrder) dao.getCustomsDesc() else dao.getCustoms()
            SortType.Preloaded -> if (_descendingOrder) dao.getPreloadedDesc() else dao.getPreloaded()
        }

         */
            /*
            if (_ascendingOrder) {
                when (it) {
                    (SortType.All && _ascendingOrder) -> dao.getAll()
                    SortType.Favorites -> dao.getFavourites()
                    SortType.Customs -> dao.getCustoms()
                    SortType.Preloaded -> dao.getPreloaded()
                }
            } else {
                when (it) {
                    SortType.All -> dao.getAllDesc()
                    SortType.Favorites -> dao.getFavouritesDesc()
                    SortType.Customs -> dao.getCustomsDesc()
                    SortType.Preloaded -> dao.getPreloadedDesc()
                }
            }

          */
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _homeUiState = MutableStateFlow(HomeUiState())

    val homeUiState = combine(_homeUiState, _sortType, _cities, _citiesDesc, _preloaded,
    ) { state, sortType, cities, citiesDesc, preloaded ->
        state.copy(
            cities = cities,
            citiesDesc = citiesDesc,
            sortType = sortType,
            preloaded = preloaded,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())

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
                _homeUiState.update { it.copy( isAddingCity = false
                ) }
            }

            CityEvent.updateOrder -> {
                _homeUiState.update { it.copy( descendingOrder = !it.descendingOrder)
                }
            }

            CityEvent.saveCity -> {
                val name = homeUiState.value.name
                val lat = homeUiState.value.lat
                val lon = homeUiState.value.lon

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
                    name = "",
                    lon = -1.0,
                    lat = -1.0,
                ) }
            }

            is CityEvent.updateFavorite -> {
                viewModelScope.launch(Dispatchers.IO){
                    if (event.city.favorite == 1) {
                        dao.removeFavoriteByID(event.city.cityId)

                       // Log.w("VIEW_MODEL", "City: ${city.lat}" )
                    } else {
                        dao.setFavoriteByID(event.city.cityId)
                    }
                }
            }

            is CityEvent.setName -> {
                _homeUiState.update { it.copy(
                    name = event.name
                ) }

            }

            CityEvent.showDialog -> {
                _homeUiState.update { it.copy( isAddingCity = true
                ) }
            }

            is CityEvent.setLat -> {
                _homeUiState.update { it.copy(
                    lat = event.lat
                ) }
            }

            is CityEvent.setLon ->  {
                _homeUiState.update { it.copy(
                    lon = event.lon
                ) }
                Log.w("VIEW_MODEL", "Update Lon to ${event.lon}")
            }
        }
    }
}

//Fiks denne
suspend fun getNearestCities(cities : Flow<List<City>>, lon : Double, lat : Double) : Map<City, Double> {

    val citiesDist : MutableMap<City, Double> = mutableMapOf()

    cities.collect { cityList ->
        cityList.forEach { city ->
            val distance = haversine(city.lat, city.lon, lat, lon)
            citiesDist[city] = distance
        }
    }

    return citiesDist.toList().sortedBy { it.second }.toMap()
}