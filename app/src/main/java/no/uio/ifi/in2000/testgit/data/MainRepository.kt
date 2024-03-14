package no.uio.ifi.in2000.testgit.data

import com.example.example.Metalerts
import com.example.example.Timeseries
import no.uio.ifi.in2000.testgit.data.metalerts.MetAlertsRepository
import no.uio.ifi.in2000.testgit.data.nowcast.NowCastRepository

class MainRepository(
    private val nowCastRepository: NowCastRepository = NowCastRepository(),
    private val metAlertsRepository: MetAlertsRepository = MetAlertsRepository()
) {

    suspend fun fetchNowCast(lat: String, lon: String):Timeseries?{
        return nowCastRepository.fetchNowCast(lat, lon)
    }

    suspend fun fetchMetAlerts(lat: String, lon: String): Metalerts {
        return metAlertsRepository.fetchMetAlerts()
    }

    suspend fun fetchAll(lat: String, lon: String) {
        fetchMetAlerts(lat, lon)
        fetchNowCast(lat, lon)
    }
}