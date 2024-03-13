package no.uio.ifi.in2000.testgit.data.NowCast

import com.example.example.Timeseries


class NowCastRepository(
    private val nowCastDataSource: NowCastDataSource = NowCastDataSource()
) {
    suspend fun fetchNowCast(): Timeseries? {
        return nowCastDataSource.getData()
    }
}