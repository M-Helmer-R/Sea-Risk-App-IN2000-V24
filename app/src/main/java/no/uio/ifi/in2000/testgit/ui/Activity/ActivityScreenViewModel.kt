package no.uio.ifi.in2000.testgit.ui.Activity

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.example.AlertFeatures
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.testgit.data.MainRepository
import no.uio.ifi.in2000.testgit.data.padleAlgoritme
import no.uio.ifi.in2000.testgit.model.nowcast.Details
import no.uio.ifi.in2000.testgit.ui.map.OceanForeCastUIState


data class NowCastUIState(
    val nowCastData: Details?
)

data class MetAlertsUIState(
    val metAlertsData: AlertFeatures?
)

data class ReccomendationUIState(
    val level: Int?
)


// this viewmodel handles api calls depending on city chosen
// this viewmodel will be created by pin clicks in HomeScreen
class ActivityScreenViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
    private val repository: MainRepository = MainRepository()

    private val _nowCastUIState = MutableStateFlow(NowCastUIState(null))
    val nowCastUIState: StateFlow<NowCastUIState> = _nowCastUIState.asStateFlow()

    private val _metAlertsUIState = MutableStateFlow(MetAlertsUIState(null))
    val metAlertsUIState: StateFlow<MetAlertsUIState> = _metAlertsUIState.asStateFlow()

    private val _oceanForecastUIState = MutableStateFlow(OceanForeCastUIState(null))
    val oceanForeCastUIState: StateFlow<OceanForeCastUIState> = _oceanForecastUIState.asStateFlow()

    private val _reccomendationUIState = MutableStateFlow(ReccomendationUIState(0))
    val reccomendationUIState: StateFlow<ReccomendationUIState> = _reccomendationUIState.asStateFlow()

    val cityName = checkNotNull(savedStateHandle["stedsnavn"])
    val lat: String = checkNotNull(savedStateHandle["lat"])
    val lon: String = checkNotNull(savedStateHandle["lon"])


    init {
        viewModelScope.launch {
            val loadAllResult = async{ loadAll(lat, lon) }
            loadAllResult.await()
            Log.i("ActivityScreenViewModel", "${_oceanForecastUIState.value.oceanDetails}")
            // should wait with algorithm calculations until apis are done
            loadRecommendationBar()
        }
    }

    private suspend fun loadRecommendationBar() {
        val level = padleAlgoritme(oceanForeCastUIState.value, nowCastUIState.value)

        val newRecommendationUIState = _reccomendationUIState.value.copy(level = level)
        _reccomendationUIState.value = newRecommendationUIState
    }
    private suspend fun loadNowCast(lat: String, lon: String){
        val nowCastData = repository.fetchNowCast(lat,lon)
        val newNowCastUIState = _nowCastUIState.value.copy(nowCastData = nowCastData)
        _nowCastUIState.value = newNowCastUIState
    }

    private suspend fun loadOceanForecast(lat: String, lon: String) {
        val oceanForecastData = repository.fetchOceanForecast(lat, lon)
        val newOceanForecastUIState = _oceanForecastUIState.value.copy(oceanDetails = oceanForecastData)
        _oceanForecastUIState.value = newOceanForecastUIState
    }

    private suspend fun loadMetAlerts(lat: String, lon: String) {
        val metAlertsData = repository.fetchMetAlerts(lat, lon)
        val newMetAlertsUIState = _metAlertsUIState.value.copy(metAlertsData = metAlertsData)
        _metAlertsUIState.value = newMetAlertsUIState
    }



    private suspend fun loadAll(lat: String, lon: String){
        loadNowCast(lat, lon)
        loadMetAlerts(lat, lon)
        loadOceanForecast(lat, lon)



    }
}

