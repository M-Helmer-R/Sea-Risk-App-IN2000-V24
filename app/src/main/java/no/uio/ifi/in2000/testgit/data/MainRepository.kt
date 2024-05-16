package no.uio.ifi.in2000.testgit.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.example.AlertFeatures
import kotlinx.coroutines.flow.Flow
import no.uio.ifi.in2000.testgit.MainApplication
import no.uio.ifi.in2000.testgit.model.nowcast.Timeseries
import no.uio.ifi.in2000.testgit.data.metalerts.MetAlertsRepository
import no.uio.ifi.in2000.testgit.data.nowcast.NowCastRepository
import no.uio.ifi.in2000.testgit.data.oceanforecast.OceanForeCastRepository
import no.uio.ifi.in2000.testgit.data.room.City
import no.uio.ifi.in2000.testgit.data.room.DatabaseRepository
import no.uio.ifi.in2000.testgit.model.nowcast.Details
import no.uio.ifi.in2000.testgit.model.oceanforecast.OceanDetails
import no.uio.ifi.in2000.testgit.ui.home.HomeViewModel
import org.jetbrains.annotations.Async

class MainRepository(

    private val nowCastRepository: NowCastRepository = NowCastRepository(),
    private val metAlertsRepository: MetAlertsRepository = MetAlertsRepository(),
    private val oceanForecastRepository: OceanForeCastRepository = OceanForeCastRepository(),
    //private var databaseRepository: DatabaseRepository,
) {




    suspend fun fetchNowCast(lat: String, lon: String): Details?{
        return nowCastRepository.fetchNowCast(lat, lon)
    }

    suspend fun fetchOceanForecast(lat: String, lon: String): OceanDetails? {
        return oceanForecastRepository.fetchOceanForeCast(lat, lon)
    }


    suspend fun fetchMetAlerts(lat: String, lon: String): AlertFeatures? {
        return metAlertsRepository.fetchMetAlerts(lat, lon)
    }


}