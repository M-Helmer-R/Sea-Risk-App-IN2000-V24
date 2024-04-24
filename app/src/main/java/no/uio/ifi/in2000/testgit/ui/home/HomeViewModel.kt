package no.uio.ifi.in2000.testgit.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
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
    private val _allCities = dao.getAll().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _preloaded = dao.getPreloaded().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _favorites = dao.getFavourites().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _userLon = MutableStateFlow(0.0)
    private val _userLat = MutableStateFlow(0.0)


    /*
    private val _cities = _sortType.flatMapLatest { it ->
        when (it) {
            SortType.All -> dao.getAll()
            SortType.Favorites -> dao.getFavourites()
            SortType.Customs -> dao.getCustoms()
            SortType.Preloaded -> dao.getPreloaded()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
     */

    private val _homeUiState = MutableStateFlow(HomeUiState())

    val homeUiState = combine(
        _homeUiState, _favorites, _preloaded, _userLon, _userLat
    ) { state, cities, preloaded, userLon, userLat ->
        state.copy(
            favorites = cities,
            preloaded = preloaded,
            //favorites = favorites,
            userLon = userLon,
            userLat = userLat,
            nearestCities = getNearestCities(preloaded, userLon, userLat)
            //nearestCities = getNearestCities(preloaded, state.userLon, state.userLat)
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())


    /*
    val homeUiState = combine(_homeUiState, _sortType, _allCities, _preloaded, _favorites, _userLat, _userLon
    ) { state, sortType, allCities, preloaded, favorites, userLat, userLon ->
        state.copy(
            sortType = sortType,
            allCities = allCities,
            preloaded = preloaded,
            favorites = favorites,
            nearestCities = getNearestCities(preloaded, userLat, userLon)
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())


    val homeUiState = combine(_homeUiState, _sortType, _favorites, _userLat, _userLon, _allCities, _preloaded,
        ) { state, sortType, favorites, userLat, userLon, allCities, preloaded ->
        state.copy(
            sortType = sortType,
            favorites = favorites,
            preloaded = preloaded,
            allCities = allCities,
            userLat = userLat,
            userLon = userLon,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())


     */
    /*
    val homeUiState = combine(
        _homeUiState,
        _sortType,
        _favorites,
        _preloaded,
        _userLat,
        _userLon
    ) { state, sortType, favorites, preloaded, userLat, userLon -> state.copy(
        favorites = favorites,
        sortType = sortType,
        //preloaded = getNearestCities(preloaded, 0.0, 0.0),
        preloaded = _preloaded,
        userLat = userLat,
        userLon = userLon,
    )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())

     */
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

                val newCity = City(name = name, lat = lat, lon = lon, customized = 1, favorite = 1)

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
                _homeUiState.update {
                    it.copy(
                        cityLat = event.lat,
                        cityLon = event.lon
                    )
                }
            }

            is HomeEvent.setUserPosition -> {
                _userLon.value = event.lon
                _userLat.value = event.lat
            }

            HomeEvent.updateNearest ->   {
                _homeUiState.update {
                    it.copy(
                        nearestCities = getNearestCities(
                            homeUiState.value.preloaded,
                            homeUiState.value.userLon,
                            homeUiState.value.userLat,
                            ),
                        isChangingPosition = false,
                        )
                }
            }

            HomeEvent.hidePositionDialog -> {
                _homeUiState.update {
                    it.copy(
                        isChangingPosition = false
                    )
                }
            }

            HomeEvent.showPositionDialog -> {
                _homeUiState.update {
                    it.copy(
                        isChangingPosition = true
                    )
                }
            }

        }
    }
    fun getNearestCities(cities : List<City>, lon : Double, lat : Double) : Map<City, Double> {

        val citiesDist : MutableMap<City, Double> = mutableMapOf<City, Double>()

        for (city in cities) {
            citiesDist.put(city, haversine(city.lat, city.lon, lat, lon))
        }

        return citiesDist.toList().sortedBy { it.second }.take(5).toMap()
    }
}

//Fiks denne
