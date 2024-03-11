package no.uio.ifi.in2000.testgit

import no.uio.ifi.in2000.testgit.data.NowCast.NowCastDataSource

suspend fun main(){
    val nowCastDataSource: NowCastDataSource = NowCastDataSource()
    val nowcastData = nowCastDataSource.getData()
    println(nowcastData)

}