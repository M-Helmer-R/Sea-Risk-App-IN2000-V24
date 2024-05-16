@file:OptIn(ExperimentalPermissionsApi::class, ExperimentalPermissionsApi::class,
    ExperimentalPermissionsApi::class, ExperimentalPermissionsApi::class,
    ExperimentalPermissionsApi::class
)

package no.uio.ifi.in2000.testgit.ui.home

import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.testgit.MainApplication
import no.uio.ifi.in2000.testgit.data.room.City
import no.uio.ifi.in2000.testgit.data.room.DatabaseRepository
import no.uio.ifi.in2000.testgit.data.room.haversine

class HomeViewModel (
    private val repository : DatabaseRepository
) : ViewModel() {

    private val _preloaded = repository.getPreLoaded().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _favorites = repository.getFavorites().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _userLat = MutableStateFlow(59.56374)
    private val _userLon = MutableStateFlow( 10.43067)
    private val _homeUiState = MutableStateFlow(HomeUiState())

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

            is HomeEvent.InsertCity -> {
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
                    viewModelScope.launch {
                        repository.saveCity(newCity)
                        Log.w("VIEW_MODEL", "City: ${newCity.lat}" )
                    }
                }
            }

            is HomeEvent.DeleteCity -> {
                viewModelScope.launch {repository.deleteCity(event.city)
                }
            }

            is HomeEvent.UpdateFavorite -> {
                viewModelScope.launch(Dispatchers.IO){
                    if (event.city.favorite == 1) {
                        repository.removeFavoriteById(event.city)

                    } else {
                        repository.setFavoriteById(event.city)
                    }
                }
            }

            HomeEvent.UpdateNearest ->   {
                _homeUiState.update {
                    it.copy(
                        nearestCities =
                        getNearestCities(
                            homeUiState.value.preloaded,
                            homeUiState.value.userLon,
                            homeUiState.value.userLat,
                        ),
                    )
                }
            }

            is HomeEvent.SetUserPosition -> {
                _userLat.value = event.lat
                _userLon.value = event.lon
            }

            HomeEvent.HidePermissionDialog -> {
                _homeUiState.update {
                    it.copy(
                        permissionDialog = false
                    )
                }
            }

            HomeEvent.ShowPermissionDialog -> {
                _homeUiState.update {
                    it.copy(
                        permissionDialog = true
                    )
                }
            }

            HomeEvent.HideDeniedPermissionDialog -> {
                _homeUiState.update {
                    it.copy(
                        deniedLocationDialog = false
                    )
                }
            }

            HomeEvent.ShowDeniedPermissionDialog -> {
                _homeUiState.update {
                    it.copy(
                        deniedLocationDialog = true
                    )
                }
            }

            HomeEvent.ShowDisabledLocationDialog -> {
                _homeUiState.update {
                    it.copy(
                        disabledLocationDialog = true
                    )
                }
            }

            HomeEvent.HideDisabledLocationDialog -> {
                _homeUiState.update {
                    it.copy(
                        disabledLocationDialog = false
                    )
                }
            }
        }
    }

    private fun getNearestCities(cities : List<City>, lon : Double, lat : Double) : Map<City, Double> {
        val citiesDist : MutableMap<City, Double> = mutableMapOf()
        cities.map { city -> citiesDist.put(city, haversine(city.lat, city.lon, lat, lon)) }
        return citiesDist.toList().sortedBy { it.second }.take(5).toMap()
    }

    @Suppress("UNCHECKED_CAST")
    companion object{
        val Factory : ViewModelProvider.Factory = object  : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras : CreationExtras
            ): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return HomeViewModel(
                    repository = (application as MainApplication).databaseRepository,
                ) as T
            }
        }
    }
}