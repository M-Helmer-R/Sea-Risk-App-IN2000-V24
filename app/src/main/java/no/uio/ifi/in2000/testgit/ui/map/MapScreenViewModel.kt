package no.uio.ifi.in2000.testgit.ui.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraState
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.testgit.data.map.GeoCodeRepository
import no.uio.ifi.in2000.testgit.data.oceanforecast.OceanForeCastRepository
import no.uio.ifi.in2000.testgit.model.oceanforecast.OceanDetails
import kotlin.math.roundToInt

data class LocationUIState(
    var loaded : Loaded = Loaded.NOTLOADED,
    var placeName: String,
    var lon: String,
    var lat: String
)

data class OceanForeCastUIState(
    var oceanDetails: OceanDetails?,
    var loaded: Loaded = Loaded.NOTLOADED
)

enum class Loaded{
    NOTLOADED,
    LOADING,
    FAILURE,
    SUCCESS
}

data class DialogUIState(
    var isVisible: Boolean?,
    var oceanLoaded: Boolean?
)

data class SearchBarUIState(
    var expanded: Boolean?
)
class MapScreenViewModel: ViewModel() {
    // Holder punktet som ble trykket på
    val mapClickLocation = MutableStateFlow<Point?>(null)

    // Tilstand for å vise/lukke popup
    val showPopup = MutableStateFlow(false)


    private val repository: GeoCodeRepository = GeoCodeRepository()
    private val oceanRepository: OceanForeCastRepository = OceanForeCastRepository()

    //SearchbarUIState
    private var _searchBarUIState = MutableStateFlow(SearchBarUIState(false))
    var searchBarUIState: StateFlow<SearchBarUIState> = _searchBarUIState.asStateFlow()

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

    @OptIn(MapboxExperimental::class)
    val mapViewportState = MapViewportState(
        CameraState(
            (Point.fromLngLat(11.49537, 64.01487)),
            EdgeInsets(3.0, 3.0, 3.0, 3.0),
            3.7,
             0.0,
            0.0

        )
    )
    // Expands dropdown of cities from searchbar
    fun expandSearchBar(){
        val newSearchBarUIState = _searchBarUIState.value.copy(expanded = true)
        _searchBarUIState.value = newSearchBarUIState
    }

    // Collapses dropdown of cities from searchbar
    fun collapseSearchBar(){
        val newSearchBarUIState = _searchBarUIState.value.copy(expanded = false)
        _searchBarUIState.value = newSearchBarUIState
    }

    fun unloadSearchUIState(){
        searchUIState.value.geocodingPlacesResponse = null
    }

    fun unloadOceanForeCastUIState(){
        oceanForeCastUIState.value.oceanDetails = null
        oceanForeCastUIState.value.loaded = Loaded.NOTLOADED
    }

    //Loads the locationnames for the searchbox
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

    //Loads oceanforecast to check if a location has ocean data
    fun loadOceanForeCast(lat: String, lon: String){
        viewModelScope.launch {
            val oceanDetails = oceanRepository.fetchOceanForeCast(lat, lon)
            println("Oceandetails: ")
            println(oceanDetails)

            if (oceanDetails != null){
                val newDialogUiState = _dialogUIState.value.copy(oceanLoaded = true)
                _dialogUIState.value = newDialogUiState
                val newOceanForeCastUIState = _oceanForeCastUIState.value.copy(oceanDetails = oceanDetails, loaded = Loaded.SUCCESS)
                _oceanForeCastUIState.value = newOceanForeCastUIState
            }

            else{
                val newOceanForeCastUIState = _oceanForeCastUIState.value.copy(oceanDetails = null, loaded = Loaded.FAILURE)
                _oceanForeCastUIState.value = newOceanForeCastUIState
            }


        }


    }

    //Unloads locationname
    fun unloadPlacename(){
        val newlocationUIState = _locationUIState.value.copy(loaded = Loaded.NOTLOADED)
        _locationUIState.value = newlocationUIState

        val newOCeanForeCastUIState = _oceanForeCastUIState.value.copy(oceanDetails = null, loaded = Loaded.NOTLOADED)
        _oceanForeCastUIState.value = newOCeanForeCastUIState
    }

    //Loads locationname from coordinates
    fun loadPlaceName2(lon: Double, lat: Double){
        viewModelScope.launch {

            val locationData = repository.reverseGeoCode2(lon, lat)
            loadOceanForeCast(lat.toString(), lon.toString())

            if (locationData != null){
                val name = locationData.formattedName.replace(Regex("[0-9]"), "")
                val newlocationUIState = _locationUIState.value.copy(placeName = name, lat = locationData.coordinates.lat.toString(), lon = locationData.coordinates.lon.toString(), loaded = Loaded.SUCCESS)
                _locationUIState.value = newlocationUIState

            }

            else{
                val lon2 = (lon*100).roundToInt()/100.0
                val lat2 = (lat*100.0).roundToInt()/100.0
                val name = "$lon2, $lat2"

                val newlocationUIState = _locationUIState.value.copy(placeName = name, lat = lat.toString(), lon = lon.toString(), loaded = Loaded.SUCCESS)
                _locationUIState.value = newlocationUIState
            }
        }

    }



    // Updates the map click location
    fun updateMapClickLocation(point: Point) {
        mapClickLocation.value = point
    }



}

