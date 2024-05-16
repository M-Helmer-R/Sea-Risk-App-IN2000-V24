package no.uio.ifi.in2000.testgit

import kotlinx.coroutines.runBlocking
import no.uio.ifi.in2000.testgit.data.nowcast.NowCastDataSource
import no.uio.ifi.in2000.testgit.data.seileAlgoritme
import org.junit.Assert
import org.junit.Test

class NowCastUnitTest {

    @Test
    fun nowCastDataSourceTest(){
        val nowCastDataSource: NowCastDataSource = NowCastDataSource()
        val oslolat = "59.911491"
        val oslolong = "10.7522"

        val result = runBlocking {
            nowCastDataSource.getData(oslolat, oslolong)
        }

        Assert.assertEquals(true, result?.airTemperature != null)


    }
}