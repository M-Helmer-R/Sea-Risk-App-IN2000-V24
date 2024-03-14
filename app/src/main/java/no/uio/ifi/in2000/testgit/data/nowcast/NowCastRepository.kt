package no.uio.ifi.in2000.testgit.data.nowcast

import com.example.example.Timeseries


class NowCastRepository(
    private val nowCastDataSource: NowCastDataSource = NowCastDataSource()
) {
    suspend fun fetchNowCast(): Timeseries? {
        return nowCastDataSource.getData()
    }
}