package no.uio.ifi.in2000.testgit.data.oceanforecast

import no.uio.ifi.in2000.testgit.model.oceanforecast.OceanTimeseries

class OceanForeCastRepository {
    val oceanForeCastDataSource: OceanForeCastDataSource = OceanForeCastDataSource()


    suspend fun fetchOceanForeCast(lat: String, lon: String): OceanTimeseries? {
        return oceanForeCastDataSource.getData(lat,lon)
    }
}