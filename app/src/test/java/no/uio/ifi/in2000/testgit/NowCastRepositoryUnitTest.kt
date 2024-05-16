package no.uio.ifi.in2000.testgit

import kotlinx.coroutines.runBlocking
import no.uio.ifi.in2000.testgit.data.nowcast.NowCastRepository
import no.uio.ifi.in2000.testgit.data.oceanforecast.OceanForeCastRepository
import org.junit.Assert
import org.junit.Test

class NowCastRepositoryUnitTest {
    @Test
    fun testNowCastRepository(){
        val nowCastRepository = NowCastRepository()

        val oslolat = "59.911491"
        val oslolong = "10.7522"

        val result = runBlocking {
            nowCastRepository.fetchNowCast(oslolat, oslolong)
        }

        Assert.assertEquals(true, result?.windSpeed != null)


    }
}