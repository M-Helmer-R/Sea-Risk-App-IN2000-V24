package no.uio.ifi.in2000.testgit



import no.uio.ifi.in2000.testgit.data.metalerts.MetAlertsDataSource
import no.uio.ifi.in2000.testgit.data.nowcast.*


suspend fun main() {
    val lat = "59.9"
    val lon = "10.7"
    val nowCastDataSource = NowCastDataSource()
    val nowcastData = nowCastDataSource.getData(lat, lon)
    println(nowcastData)
    //val metAlertsDataSource: MetAlertsDataSource = MetAlertsDataSource()
    //val metAlertsData = metAlertsDataSource.getMetAlerts()
    //println(metAlertsData)
}
