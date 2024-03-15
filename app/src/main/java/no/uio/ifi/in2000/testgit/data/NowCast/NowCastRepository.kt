package no.uio.ifi.in2000.testgit.data.nowcast

import com.example.example.Timeseries
import no.uio.ifi.in2000.testgit.data.nowcast.NowCastDataSource


class NowCastRepository(private val nowCastDataSource: NowCastDataSource = NowCastDataSource()) {
    suspend fun fetchNowCast(lat: String, lon: String): Timeseries? {
        return nowCastDataSource.getData(lat, lon)

    }
}