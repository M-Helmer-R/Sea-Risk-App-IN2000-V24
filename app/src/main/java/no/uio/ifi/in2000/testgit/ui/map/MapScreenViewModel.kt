package no.uio.ifi.in2000.testgit.ui.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.testgit.data.map.GeoCodeRepository
import no.uio.ifi.in2000.testgit.data.map.ReverseGeocodeCallback
import no.uio.ifi.in2000.testgit.data.oceanforecast.OceanForeCastRepository
import no.uio.ifi.in2000.testgit.model.oceanforecast.OceanDetails
import no.uio.ifi.in2000.testgit.model.oceanforecast.OceanTimeseries

data class LocationUIState(

    var placeName: String,
    var lon: String,
    var lat: String
)

data class OceanForeCastUIState(
    var oceanDetails: OceanDetails?
)

data class DialogUIState(
    var isVisible: Boolean?,
    var oceanLoaded: Boolean?
)
class MapScreenViewModel: ViewModel() {
    private val repository: GeoCodeRepository = GeoCodeRepository()
    private val oceanRepository: OceanForeCastRepository = OceanForeCastRepository()

    //Locationuistate for clicking a point on map
    private var _locationUIState = MutableStateFlow(LocationUIState(placeName = "Ingen data", lat = "0", lon = "0"))
    var locationUIState: StateFlow<LocationUIState> = _locationUIState.asStateFlow()


    //Dialoguistate
    private var _dialogUIState = MutableStateFlow(DialogUIState(isVisible = false, null))
    var dialogUIState: StateFlow<DialogUIState> = _dialogUIState.asStateFlow()

    //Oceanforecast uistate
    private var _oceanForeCastUIState = MutableStateFlow(OceanForeCastUIState(null))
    var oceanForeCastUIState: StateFlow<OceanForeCastUIState> = _oceanForeCastUIState.asStateFlow()


    //Locationuistate for search
    private var _searchUIState = MutableStateFlow(SearchUIState(null))
    var searchUIState: StateFlow<SearchUIState> = _searchUIState.asStateFlow()

    fun unloadSearchUIState(){
        searchUIState.value.geocodingPlacesResponse = null
    }
    fun loadSearchUIState(searchString: String){
        viewModelScope.launch {
            val geocodingPlacesResponse = repository.searchGeoCode(searchString)
            Log.i("Mapscreenviewmodel", "Loaduistate called")
            if (geocodingPlacesResponse != null){
                Log.i("MapscreenViewModel", "geocodingplaceresponse is not null")
                val newSearchUIState = _searchUIState.value.copy(geocodingPlacesResponse = geocodingPlacesResponse)
                _searchUIState.value = newSearchUIState
            }
        }
    }
    fun loadOceanForeCast(lat: String, lon: String){
        viewModelScope.launch {
            val oceanDetails = oceanRepository.fetchOceanForeCast(lat, lon)
            println("Oceandetails: ")
            println(oceanDetails)

            if (oceanDetails != null){
                val newDialogUiState = _dialogUIState.value.copy(oceanLoaded = true)
                _dialogUIState.value = newDialogUiState
            }

            else{
                val newDialogUiState = _dialogUIState.value.copy(oceanLoaded = false)
                _dialogUIState.value = newDialogUiState
            }

            val newOceanForeCastUIState = _oceanForeCastUIState.value.copy(oceanDetails = oceanDetails)
            _oceanForeCastUIState.value = newOceanForeCastUIState
        }


    }
    fun loadPlaceName(lon: Double, lat: Double){

        repository.reverseGeoCode(lon, lat, object: ReverseGeocodeCallback {
            override fun onSuccess(placeName: String) {
                val newlocationUIState = _locationUIState.value.copy(placeName = placeName, lat = lat.toString(), lon = lon.toString())
                _locationUIState.value = newlocationUIState
                loadOceanForeCast(lat.toString(), lon.toString())
                showDialog()
            }

            override fun onFailure(placeName: String) {
                val newlocationUIState = _locationUIState.value.copy(placeName = placeName)
                _locationUIState.value = newlocationUIState
                loadOceanForeCast(lat.toString(), lon.toString())
                //showDialog()
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

        dialogUIState.value.oceanLoaded = null

        println("Dialog hidden")
    }


}