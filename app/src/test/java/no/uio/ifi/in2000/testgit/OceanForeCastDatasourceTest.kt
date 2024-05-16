package no.uio.ifi.in2000.testgit

import kotlinx.coroutines.runBlocking
import no.uio.ifi.in2000.testgit.data.oceanforecast.OceanForeCastDataSource
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class OceanForeCastDatasourceTest {

    @Test
    fun testOceanForeCastDataSource() {
        val oceanForeCastDataSource: OceanForeCastDataSource = OceanForeCastDataSource()

        val oslolat = "59.911491"
        val oslolong = "10.7522"

        val apicall = runBlocking { oceanForeCastDataSource.getData(oslolat, oslolong) }
        val result = (apicall?.seaWaterTemperature != null)

        Assert.assertEquals(true, result)
    }
}