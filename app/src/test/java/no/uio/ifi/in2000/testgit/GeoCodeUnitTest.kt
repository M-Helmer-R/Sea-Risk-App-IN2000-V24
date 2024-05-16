package no.uio.ifi.in2000.testgit

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import no.uio.ifi.in2000.testgit.data.map.GeoCodeDataSource
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GeoCodeUnitTest {
    @Test
    @ExperimentalCoroutinesApi
    fun testReverseGeoCode(){
        val geoCodeDataSource: GeoCodeDataSource = GeoCodeDataSource()
        val oslolat = 59.911491
        val oslolong = 10.7522

        val apicall = runBlocking{geoCodeDataSource.reverseGeoCode2(oslolong, oslolat)}
        val expected = "0154 Oslo, Norway"
        val result = (apicall?.formattedName == expected)
        Assert.assertEquals(true, result)

    }
    @Test
    @ExperimentalCoroutinesApi
    fun testSearchGeoCode(){
        val geoCodeDataSource: GeoCodeDataSource = GeoCodeDataSource()
        val searchstring = "os"

        val apicall = runBlocking{geoCodeDataSource.searchGeoCode(searchstring)}

        val result = apicall?.features?.isNotEmpty()
        Assert.assertEquals(true, result)

    }
}