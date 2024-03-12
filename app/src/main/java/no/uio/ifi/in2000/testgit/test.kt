package no.uio.ifi.in2000.testgit

import no.uio.ifi.in2000.testgit.data.nowcast.DataSourceTest

suspend fun main(){
    val dataSourceTest: DataSourceTest = DataSourceTest()
    val nowcastData = dataSourceTest.getData()
    println(nowcastData)

}