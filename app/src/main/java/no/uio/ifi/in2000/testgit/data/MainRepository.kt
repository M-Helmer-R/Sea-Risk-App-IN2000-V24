package no.uio.ifi.in2000.testgit.data

import com.example.example.Timeseries
import no.uio.ifi.in2000.testgit.data.NowCast.NowCastRepository

class MainRepository(
    private val nowCastRepository: NowCastRepository = NowCastRepository()
) {

    suspend fun fetchNowCast():Timeseries?{
        return nowCastRepository.fetchNowCast()
    }
}