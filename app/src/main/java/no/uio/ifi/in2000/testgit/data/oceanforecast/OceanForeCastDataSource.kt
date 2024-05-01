package no.uio.ifi.in2000.testgit.data.oceanforecast



import com.example.example.NowcastData
import com.example.example.Timeseries
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.serialization.gson.gson
import no.uio.ifi.in2000.testgit.model.oceanforecast.OceanForeCastData
import no.uio.ifi.in2000.testgit.model.oceanforecast.OceanProperties
import no.uio.ifi.in2000.testgit.model.oceanforecast.OceanTimeseries

interface OceanForeCastCallBack {
    fun onSuccess(placeName: String)
    fun onFailure(placeName: String)
}
class OceanForeCastDataSource {
    val client = HttpClient() {
        install(ContentNegotiation) {
            gson()
        }
        defaultRequest {
            url("https://gw-uio.intark.uh-it.no/in2000/")

            header("X-Gravitee-API-Key", "71f2d9f6-8fbf-480e-b098-e905dcb29a51")
        }

    }

    suspend fun getData(lat: String, lon: String): OceanTimeseries? {
        try {
            val oceanforecastOslo = "weatherapi/oceanforecast/2.0/complete?lat=$lat&lon=$lon"
            val kallNowcastOslo = client.get(oceanforecastOslo)

            val dataNowcastOslo = kallNowcastOslo.body<OceanForeCastData>()
            //val instantNowcastData = dataNowcastOslo.timeseries[0]


            return dataNowcastOslo.properties?.timeseries?.get(0)
        }

        catch (e: Exception){
            return null
        }

    }
}