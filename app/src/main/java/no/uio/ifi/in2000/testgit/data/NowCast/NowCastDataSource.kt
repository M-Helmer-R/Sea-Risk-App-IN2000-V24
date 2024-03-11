package no.uio.ifi.in2000.testgit.data.NowCast

import com.example.example.NowcastData
import com.example.example.Timeseries
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.serialization.gson.gson

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


    suspend fun getData(): Timeseries? {
        val nowcastOslo = "weatherapi/nowcast/2.0/complete?lat=59.9139&lon=10.7522"
        val kallNowcastOslo = client.get(nowcastOslo)
        val dataNowcastOslo = kallNowcastOslo.body<NowcastData>()
        val instantNowcastData = dataNowcastOslo.properties?.timeseries?.get(0)

        return instantNowcastData
    }

}