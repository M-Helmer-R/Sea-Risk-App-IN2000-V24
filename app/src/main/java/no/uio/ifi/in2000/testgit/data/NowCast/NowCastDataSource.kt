package no.uio.ifi.in2000.testgit.data.nowcast

import android.util.Log
import no.uio.ifi.in2000.testgit.model.nowcast.NowcastData
import no.uio.ifi.in2000.testgit.model.nowcast.Timeseries
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.isSuccess
import io.ktor.serialization.gson.gson
import no.uio.ifi.in2000.testgit.model.nowcast.Details

class NowCastDataSource {
    val client = HttpClient() {
        install(ContentNegotiation) {
            gson()
        }
        defaultRequest {
            url("https://gw-uio.intark.uh-it.no/in2000/")

            header("X-Gravitee-API-Key", "71f2d9f6-8fbf-480e-b098-e905dcb29a51")
        }

    }


    suspend fun getData(lat: String, lon: String): Details? {
        try{
            val nowcastOslo = "weatherapi/nowcast/2.0/complete?lat=$lat&lon=$lon"
            val callNowcastOslo = client.get(nowcastOslo)
            val dataNowcastOslo = callNowcastOslo.body<NowcastData>()
            return dataNowcastOslo.Properties?.timeseries?.get(0)?.data?.instant?.details
        }
        catch(e:Exception) {
            Log.i("Nowcastdatasource", "api call failed")
            return null
        }
    }
}
