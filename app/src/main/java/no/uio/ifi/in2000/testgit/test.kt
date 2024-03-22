package no.uio.ifi.in2000.testgit



import no.uio.ifi.in2000.testgit.data.metalerts.MetAlertsDataSource
import no.uio.ifi.in2000.testgit.data.nowcast.*
import no.uio.ifi.in2000.testgit.data.oceanforecast.OceanForeCastDataSource
import no.uio.ifi.in2000.testgit.model.CityDatabase.CityDatabase


suspend fun main() {
    val lat = "65.5899"
    val lon = "11.9876"
    val oslolat = CityDatabase.OSLO.lat
    val oslolon = CityDatabase.OSLO.lon
    //val nowCastDataSource = NowCastDataSource()
    //val nowcastData = nowCastDataSource.getData(lat, lon)
    //println(nowcastData)
    val metAlertsDataSource: MetAlertsDataSource = MetAlertsDataSource()
    val metAlertsData = metAlertsDataSource.getMetAlerts(lat, lon)
    val oceanForeCastDataSource: OceanForeCastDataSource = OceanForeCastDataSource()
    val oceanForeCastData = oceanForeCastDataSource.getData()
    if (oceanForeCastData != null) {
        print(oceanForeCastData.data?.instant?.details?.seaWaterSpeed)
    }
    /*if (metAlertsData != null) {
        if(metAlertsData.isEmpty() == true) {
            println("Ingen varsler")
        } else {
            println(metAlertsData[0].alertProperties?.awarenessType)

        }
    }

     */
    /*
    if (metAlertsData != null) {
        println(metAlertsData.alertProperties?.awarenessType)
    } else {
        println("ingen varsler")
    }

     */
}

