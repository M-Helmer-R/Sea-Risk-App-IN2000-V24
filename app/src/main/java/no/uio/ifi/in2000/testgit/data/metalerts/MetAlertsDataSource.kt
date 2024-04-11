package no.uio.ifi.in2000.testgit.data.metalerts

import com.example.example.AlertFeatures
import com.example.example.Metalerts
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.isSuccess
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
        val metAlerts = "weatherapi/metalerts/2.0/current.json?lat=$lat&lon=$lon"
        val callMetAlerts = client.get(metAlerts)
        val dataMetalerts: Metalerts
        // if response is successful, complete the apicall and create metalerts objects



        if(callMetAlerts.status.isSuccess()) {
            dataMetalerts = callMetAlerts.body<Metalerts>()
        } else {
            dataMetalerts = Metalerts(features = emptyList(), lang = null, lastChange = null, type = null)
        }

        // depending on metalertsobject,
        if(dataMetalerts.features?.isEmpty() == false) {
            return dataMetalerts.features[0]
        } else {
            return null

        }
        //val currentMetAlerts = dataMetAlerts.features.get(0)


    }

}