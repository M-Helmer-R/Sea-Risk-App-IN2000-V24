package no.uio.ifi.in2000.testgit.data.metalerts

import com.example.example.Metalerts

class MetAlertsRepository {
    val metAlertsDataSource: MetAlertsDataSource = MetAlertsDataSource()

    suspend fun fetchMetAlerts(): Metalerts {
        return metAlertsDataSource.getMetAlerts()
    }
}