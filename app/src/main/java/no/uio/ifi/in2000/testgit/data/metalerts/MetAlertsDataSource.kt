package no.uio.ifi.in2000.testgit.data.metalerts

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


    suspend fun getMetAlerts(): Metalerts {
        //if httpresponse.status == httpresponsecode.ok
        //val metAlerts = "weatherapi/metalerts/2.0/current.json"
        val metAlerts = "weatherapi/metalerts/2.0/current.json?lat=59.9&lon=10.74"

        val kallMetAlerts = client.get(metAlerts)
        val dataMetAlerts = kallMetAlerts.body<Metalerts>()
        //val currentMetAlerts = dataMetAlerts.features.get(0)

        return dataMetAlerts
    }

}