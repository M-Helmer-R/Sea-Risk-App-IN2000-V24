package no.uio.ifi.in2000.testgit


import no.uio.ifi.in2000.testgit.data.metalerts.MetAlertsDataSource
//import no.uio.ifi.in2000.testgit.data.nowcast.NowCastDataSource

suspend fun main() {
    //val nowCastDataSource: NowCastDataSource = NowCastDataSource()
    //val nowcastData = nowCastDataSource.getData()
    //println(nowcastData)
    val metAlertsDataSource: MetAlertsDataSource = MetAlertsDataSource()
    val metAlertsData = metAlertsDataSource.getMetAlerts()
    println(metAlertsData)
}
