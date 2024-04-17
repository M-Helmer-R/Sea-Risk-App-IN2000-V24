package no.uio.ifi.in2000.testgit.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.testgit.data.MainRepository
import no.uio.ifi.in2000.testgit.data.map.GeoCodeRepository
import no.uio.ifi.in2000.testgit.data.map.ReverseGeocodeCallback
import no.uio.ifi.in2000.testgit.ui.Activity.NowCastUIState

data class LocationUIState(
    var placeName: String
)

data class DialogUIState(
    var isVisible: Boolean?
)
class MapScreenViewModel: ViewModel() {
    private val repository: GeoCodeRepository = GeoCodeRepository()

    private var _locationUIState = MutableStateFlow(LocationUIState("Ingen data"))
    var locationUIState: StateFlow<LocationUIState> = _locationUIState.asStateFlow()

    private var _dialogUIState = MutableStateFlow(DialogUIState(false))
    var dialogUIState: StateFlow<DialogUIState> = _dialogUIState.asStateFlow()


    fun loadPlaceName(lon: Double, lat: Double){

        repository.reverseGeoCode(lon, lat, object: ReverseGeocodeCallback {
            override fun onSuccess(placeName: String) {
                val newlocationUIState = _locationUIState.value.copy(placeName = placeName)
                _locationUIState.value = newlocationUIState
                showDialog()
            }

            override fun onFailure(placeName: String) {
                val newlocationUIState = _locationUIState.value.copy(placeName = placeName)
                _locationUIState.value = newlocationUIState
                showDialog()
            }
        } )

    }


    private fun showDialog(){
        val newdialogUIState = _dialogUIState.value.copy(isVisible = true)
        _dialogUIState.value = newdialogUIState
        println(newdialogUIState)
    }

    fun hideDialog(){
        val newdialogUIState = _dialogUIState.value.copy(isVisible = false)
        _dialogUIState.value = newdialogUIState
        println("Dialog hidden")
    }

    fun dismissDialog(){
        print("Dialog dismissed")
        viewModelScope.launch {
            hideDialog()
        }
    }

    fun getDialog(){
        viewModelScope.launch {
            showDialog()
        }
    }

    private suspend fun handleLoadPlace(lon: Double, lat: Double){
        viewModelScope.launch {
            loadPlaceName(lon, lat)
        }
    }
}