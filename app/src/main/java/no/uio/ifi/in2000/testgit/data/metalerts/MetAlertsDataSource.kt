package no.uio.ifi.in2000.testgit.data.metalerts

import android.util.Log
import com.example.example.AlertFeatures
import com.example.example.Metalerts
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.serialization.gson.gson

class MetAlertsDataSource {
    val client = HttpClient() {
        install(ContentNegotiation) {
            gson()
        }
        defaultRequest {
            url("https://gw-uio.intark.uh-it.no/in2000/")

            header("X-Gravitee-API-Key", "71f2d9f6-8fbf-480e-b098-e905dcb29a51")
        }

    }


    suspend fun getMetAlerts(lat: String, lon: String): AlertFeatures? {
        try {
            val metAlerts = "weatherapi/metalerts/2.0/current.json?lat=$lat&lon=$lon"
            val metAlertsResponse = client.get(metAlerts)

            val metAlertsData = metAlertsResponse.body<Metalerts>()
            return metAlertsData.features?.get(0)


        } catch( e: Exception){
            Log.i("MetAlertsDataSource", "FAILED")
            return null
        }
    }

}