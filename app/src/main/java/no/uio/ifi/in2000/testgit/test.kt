package no.uio.ifi.in2000.testgit

import android.util.Log
import com.example.example.NowcastData
import no.uio.ifi.in2000.testgit.data.DataSourceTest

suspend fun main(){
    val dataSourceTest: DataSourceTest = DataSourceTest()
    val nowcastData = dataSourceTest.getData()
    println(nowcastData)

}