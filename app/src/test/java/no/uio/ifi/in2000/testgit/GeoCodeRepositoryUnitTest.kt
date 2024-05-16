package no.uio.ifi.in2000.testgit

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import no.uio.ifi.in2000.testgit.data.map.GeoCodeDataSource
import no.uio.ifi.in2000.testgit.data.map.GeoCodeRepository
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GeoCodeRepositoryUnitTest {
    @Test
    @ExperimentalCoroutinesApi
    fun testReverseGeoCodeRepository(){
        val geoCodeRepository = GeoCodeRepository()
        val oslolat = 59.911491
        val oslolong = 10.7522

        val apicall = runBlocking{
            geoCodeRepository.reverseGeoCode2(oslolong, oslolat)

        }
        val expected = "0154 Oslo, Norway"
        val result = (apicall?.formattedName == expected)
        Assert.assertEquals(true, result)

    }
    @Test
    @ExperimentalCoroutinesApi
    fun testSearchGeoCodeRepository(){
        val geoCodeRepository = GeoCodeRepository()
        val searchstring = "os"

        val apicall = runBlocking{
            geoCodeRepository.searchGeoCode(searchstring)
        }

        val result = apicall?.features?.isNotEmpty()
        Assert.assertEquals(true, result)

    }
    
}