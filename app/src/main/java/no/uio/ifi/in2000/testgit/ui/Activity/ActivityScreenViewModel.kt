package no.uio.ifi.in2000.testgit.ui.Activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.example.Metalerts
import com.example.example.Timeseries
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.testgit.data.MainRepository

// this viewmodel handles api calls depending on city chosen
// this viewmodel will be created by pin clicks in HomeScreen
class ActivityScreenViewModel(lat: String, lon: String, navn: String): ViewModel() {
    private val repository: MainRepository = MainRepository()

    private var _nowCastUIState = MutableStateFlow(NowCastUIState(null))
    var nowCastUIState: StateFlow<NowCastUIState> = _nowCastUIState.asStateFlow()

    private val _metAlertsUIState = MutableStateFlow(MetAlertsUIState(null))
    val metAlertsUIState: StateFlow<MetAlertsUIState> = _metAlertsUIState.asStateFlow()

    init {
        viewModelScope.launch {
            loadAll(lat, lon)
        }
    }
    private suspend fun loadNowCast(lat: String, lon: String){
        println("RIGHT BEFORE ATTEMPT: ActivityScreenViewModel: repository.fetchNowcast")
        val nowCastData = repository.fetchNowCast(lat, lon)
        println("ActivityScreenViewModel: repository.fetchNowcast")
        val newNowCastUIState = _nowCastUIState.value.copy(nowCastData = nowCastData)
        _nowCastUIState.value = newNowCastUIState
    }

    private suspend fun loadMetAlerts(lat: String, lon: String) {
        val metAlertsData = repository.fetchMetAlerts(lat, lon)
        val newMetAlertsUIState = _metAlertsUIState.value.copy(metAlertsData = metAlertsData)
        _metAlertsUIState.value = newMetAlertsUIState
    }

    private suspend fun loadAll(lat:String, lon: String){
        loadNowCast(lat, lon)
        loadMetAlerts(lat, lon)


    }
}

data class NowCastUIState(
    val nowCastData: Timeseries?
)

data class MetAlertsUIState(
    val metAlertsData: Metalerts?
)