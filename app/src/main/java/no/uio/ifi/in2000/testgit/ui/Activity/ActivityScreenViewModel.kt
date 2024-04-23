package no.uio.ifi.in2000.testgit.ui.Activity

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.example.AlertFeatures
import com.example.example.Timeseries
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.testgit.data.MainRepository
import no.uio.ifi.in2000.testgit.model.CityDatabase.CityDatabase

// this viewmodel handles api calls depending on city chosen
// this viewmodel will be created by pin clicks in HomeScreen
class ActivityScreenViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
    private val repository: MainRepository = MainRepository()

    private var _nowCastUIState = MutableStateFlow(NowCastUIState(null))
    var nowCastUIState: StateFlow<NowCastUIState> = _nowCastUIState.asStateFlow()

    private val _metAlertsUIState = MutableStateFlow(MetAlertsUIState(null))
    val metAlertsUIState: StateFlow<MetAlertsUIState> = _metAlertsUIState.asStateFlow()

    val cityName = checkNotNull(savedStateHandle["cityName"])

    init {
        viewModelScope.launch {
            loadAll()
        }
    }
    private suspend fun loadNowCast(){
        // prøv å bruke .find, så slipper du en lang when statement
        val nowCastData = when(cityName) {
            "Oslo" -> repository.fetchNowCast(CityDatabase.OSLO.lat, CityDatabase.OSLO.lon)
            "Bergen" -> repository.fetchNowCast(CityDatabase.Bergen.lat, CityDatabase.Bergen.lon)
            "Kristiansand" -> repository.fetchNowCast(CityDatabase.KRISTIANSAND.lat, CityDatabase.KRISTIANSAND.lon)
            "Stavanger" -> repository.fetchNowCast(CityDatabase.STAVANGER.lat, CityDatabase.STAVANGER.lon)
            "Kragerø" -> repository.fetchNowCast(CityDatabase.KRAGERØ.lat, CityDatabase.KRAGERØ.lon)
            "Arendal" -> repository.fetchNowCast(CityDatabase.ARENDAL.lat, CityDatabase.ARENDAL.lon)
            "Rørvik - Støtt" -> repository.fetchNowCast(CityDatabase.FARE.lat, CityDatabase.FARE.lon)
            else -> null
        }


        println("RIGHT BEFORE ATTEMPT: ActivityScreenViewModel: repository.fetchNowcast")
        //val nowCastData = repository.fetchNowCast(lat, lon)
        println("ActivityScreenViewModel: repository.fetchNowcast")
        val newNowCastUIState = _nowCastUIState.value.copy(nowCastData = nowCastData)
        _nowCastUIState.value = newNowCastUIState
    }


    private suspend fun loadMetAlerts() {
        val metAlertsData = when(cityName) {
            "Oslo" -> repository.fetchMetAlerts(
                CityDatabase.OSLO.lat,
                CityDatabase.OSLO.lon)
            "Bergen" -> repository.fetchMetAlerts(
                CityDatabase.Bergen.lat,
                CityDatabase.Bergen.lon)
            "Kristiansand" -> repository.fetchMetAlerts(
                CityDatabase.KRISTIANSAND.lat,
                CityDatabase.KRISTIANSAND.lon
            )
            "Stavanger" -> repository.fetchMetAlerts(
                CityDatabase.STAVANGER.lat,
                CityDatabase.STAVANGER.lon
            )
            "Kragerø" -> repository.fetchMetAlerts(
                CityDatabase.KRAGERØ.lat,
                CityDatabase.KRAGERØ.lon
            )
            "Arendal" -> repository.fetchMetAlerts(
                CityDatabase.ARENDAL.lat,
                CityDatabase.ARENDAL.lon
            )
            "Rørvik - Støtt" -> repository.fetchMetAlerts(
                CityDatabase.FARE.lat,
                CityDatabase.FARE.lon
            )
            else -> null
        }
        val newMetAlertsUIState = _metAlertsUIState.value.copy(metAlertsData = metAlertsData)
        _metAlertsUIState.value = newMetAlertsUIState
    }



    private suspend fun loadAll(){
        loadNowCast()
        loadMetAlerts()


    }
}

data class NowCastUIState(
    val nowCastData: Timeseries?
)

data class MetAlertsUIState(
    val metAlertsData: AlertFeatures?
)