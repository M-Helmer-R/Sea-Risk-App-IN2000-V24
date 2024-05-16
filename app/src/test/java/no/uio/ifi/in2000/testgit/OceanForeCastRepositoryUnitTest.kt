package no.uio.ifi.in2000.testgit

import kotlinx.coroutines.runBlocking
import no.uio.ifi.in2000.testgit.data.oceanforecast.OceanForeCastRepository
import org.junit.Assert
import org.junit.Test

class OceanForeCastRepositoryUnitTest {
    @Test
    fun testOceanForecastRepository(){
        val oceanForeCastRepository = OceanForeCastRepository()

        val oslolat = "59.911491"
        val oslolong = "10.7522"

        val result = runBlocking {
            oceanForeCastRepository.fetchOceanForeCast(oslolat, oslolong)
        }

        Assert.assertEquals(true, result?.seaWaterTemperature != null)


    }
}