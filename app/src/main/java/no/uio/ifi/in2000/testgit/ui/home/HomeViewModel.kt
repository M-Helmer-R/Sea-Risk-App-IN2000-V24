package no.uio.ifi.in2000.testgit.ui.home

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.testgit.data.room.City
import no.uio.ifi.in2000.testgit.data.room.CityDao
import no.uio.ifi.in2000.testgit.data.room.DatabaseRepository
import no.uio.ifi.in2000.testgit.data.room.haversine

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel (
    private val dao : CityDao,
) : ViewModel() {

    private val repository : DatabaseRepository = DatabaseRepository(dao)

    private val _allCities = repository.getAll().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _preloaded = repository.getPreLoaded().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _favorites = repository.getFavorites().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _userLon = MutableStateFlow(0.0)
    private val _userLat = MutableStateFlow(0.0)
    private val _homeUiState = MutableStateFlow(HomeUiState())

    //Location
    val permissionDialogQueue = mutableStateListOf<String>()

    fun dismissDialog(){
        permissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission : String,
        isGranted : Boolean
    ){
        if (!isGranted && !permissionDialogQueue.contains(permission)){
            permissionDialogQueue.add(permission)
        }
    }

    val homeUiState = combine(
        _homeUiState, _favorites, _preloaded, _userLon, _userLat
    ) { state , favorites, preloaded, userLon, userLat ->
        state.copy(
            favorites = favorites,
            preloaded = preloaded,
            userLon = userLon,
            userLat = userLat,
            nearestCities = getNearestCities(preloaded, userLon, userLat)
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())

    fun onEvent( event : HomeEvent){
        when (event) {
            is HomeEvent.DeleteHome -> {
                viewModelScope.launch {repository.deleteCity(event.city)
                }
            }

            HomeEvent.showAddCityDialog -> {
                _homeUiState.update {
                    it.copy( isAddingCity = true
                    )
                }
            }

            HomeEvent.hideAddCityDialog -> {
                _homeUiState.update { it.copy( isAddingCity = false
                ) }
            }

            is HomeEvent.updateFavorite -> {
                viewModelScope.launch(Dispatchers.IO){
                    if (event.city.favorite == 1) {
                        repository.removeFavorite(event.city)

                    } else {
                        repository.setFavorite(event.city)
                    }
                }
            }

            is HomeEvent.setName -> {
                _homeUiState.update {
                    it.copy(
                        cityName = event.name
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
                        nearestCities =
                        getNearestCities(
                            homeUiState.value.preloaded,
                            homeUiState.value.userLon,
                            homeUiState.value.userLat,
                            ),
                        locationDialog = false,
                        )
                }
            }

            HomeEvent.hideManualLocationDialog -> {
                _homeUiState.update {
                    it.copy(
                        locationDialog = false
                    )
                }
            }

            HomeEvent.showManualLocationDialog -> {
                _homeUiState.update {
                    it.copy(
                        locationDialog = true
                    )
                }
            }


            is HomeEvent.OpenActivity -> {
                TODO()
            }

            is HomeEvent.setCityLat -> {
                _homeUiState.update {
                    it.copy(
                        cityLat = event.lat
                    )
                }
            }

            is HomeEvent.setCityLon -> {
                _homeUiState.update {
                    it.copy(
                        cityLon = event.lon
                    )
                }
            }

            is HomeEvent.insertCity -> {
                val name = event.name
                val lat : Double? = event.lat.toDoubleOrNull()
                val lon : Double? = event.lon.toDoubleOrNull()

                Log.w("VIEW_MODEL", "Name: $name, Lat: $lat, Lon: $lon" )

                if (event.name.isBlank() || lat == null|| lon == null){
                    return
                } else {

                    val newCity = City(
                        name = name,
                        lat = lat,
                        lon = lon,
                        customized = 1,
                        favorite = 1
                    )

                    //Sjekk her
                    viewModelScope.launch {
                        repository.saveCity(newCity)
                        Log.w("VIEW_MODEL", "City: ${newCity.lat}" )
                    }

                    _homeUiState.update { it.copy(
                        isAddingCity = false,
                        cityName = "",
                        cityLon = "",
                        cityLat = "",
                        nameError = false,
                        latError = false,
                        lonError = false
                    ) }
                }

            }

            HomeEvent.setNameError -> {
                _homeUiState.update { it.copy(
                    nameError = true
                ) }
            }
            HomeEvent.setLatError -> {
                _homeUiState.update { it.copy(
                    latError = true
                ) }
            }
            HomeEvent.setLonError -> {
                _homeUiState.update { it.copy(
                    lonError = true
                ) }
            }

            HomeEvent.hidePermissionDialog -> {
                _homeUiState.update {
                    it.copy(
                        permissionDialog = false
                    )
                }
            }

            HomeEvent.showPermissionDialog -> {
                _homeUiState.update {
                    it.copy(
                        permissionDialog = true
                    )
                }
            }

            HomeEvent.hideLocationDialog -> {
                _homeUiState.update {
                    it.copy(
                        locationDialog = false
                    )
                }
            }
            HomeEvent.showLocationDialog -> {
                _homeUiState.update {
                    it.copy(
                        locationDialog = true
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

    fun requestLocation(){
    }
}

//Fiks denne
