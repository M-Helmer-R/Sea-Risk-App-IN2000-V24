package no.uio.ifi.in2000.testgit.data.metalerts

import com.example.example.AlertFeatures
import com.example.example.Metalerts

class MetAlertsRepository {
    val metAlertsDataSource: MetAlertsDataSource = MetAlertsDataSource()

    suspend fun fetchMetAlerts(lat: String, lon: String): AlertFeatures? {
        return metAlertsDataSource.getMetAlerts(lat, lon)
    }
}