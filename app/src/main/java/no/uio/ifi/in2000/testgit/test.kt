package no.uio.ifi.in2000.testgit



import no.uio.ifi.in2000.testgit.data.metalerts.MetAlertsDataSource

import no.uio.ifi.in2000.testgit.data.oceanforecast.OceanForeCastDataSource


suspend fun main() {
    val lat = "59.9"
    val lon = "10.7"
    val oceanForeCastDataSource = OceanForeCastDataSource()
    val oceanData = oceanForeCastDataSource.getData()
    println(oceanData)
    //val metAlertsDataSource: MetAlertsDataSource = MetAlertsDataSource()
    //val metAlertsData = metAlertsDataSource.getMetAlerts()
    //println(metAlertsData)
}
