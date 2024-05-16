package no.uio.ifi.in2000.testgit.ui.home

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import no.uio.ifi.in2000.testgit.MainApplication
import no.uio.ifi.in2000.testgit.data.location.LocationRepository

//Location view model
data class LocationViewModel(
    private val locationRepository: LocationRepository
) : ViewModel() {
    private val _location = MutableLiveData<Location?>()
    val location: LiveData<Location?> = _location

    fun fetchLocation() {
        locationRepository.getUserLocation { loc ->
            _location.postValue(loc)
        }
    }

    //Location view model factory
    @Suppress("UNCHECKED_CAST")
    companion object{
        val Factory : ViewModelProvider.Factory = object  : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras : CreationExtras
            ): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return LocationViewModel(
                    locationRepository = (application as MainApplication).locationRepository,
                ) as T
            }
        }
    }
}