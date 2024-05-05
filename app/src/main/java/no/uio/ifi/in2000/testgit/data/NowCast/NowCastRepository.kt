package no.uio.ifi.in2000.testgit.data.nowcast

import no.uio.ifi.in2000.testgit.model.nowcast.Details
import no.uio.ifi.in2000.testgit.model.nowcast.Timeseries


class NowCastRepository(private val nowCastDataSource: NowCastDataSource = NowCastDataSource()) {
    suspend fun fetchNowCast(lat: String, lon: String): Details? {
        return nowCastDataSource.getData(lat, lon)

    }
}