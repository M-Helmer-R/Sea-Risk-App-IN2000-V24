package no.uio.ifi.in2000.testgit.data.map

import android.util.Log
import com.mapbox.api.geocoding.v5.GeocodingCriteria
import com.mapbox.api.geocoding.v5.MapboxGeocoding
import com.mapbox.api.geocoding.v5.models.GeocodingResponse
import com.mapbox.geojson.Point
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.serialization.gson.gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface ReverseGeocodeCallback {
    fun onSuccess(placeName: String)
    fun onFailure(placeName: String)
}
class GeoCodeDataSource {
    val client = HttpClient() {
        install(ContentNegotiation) {
            gson()
        }


    }

    suspend fun reverseGeoCode2(lon: Double, lat: Double): Properties? {

        try{
            val clickURL = "https://api.mapbox.com/search/geocode/v6/reverse?longitude=$lon&latitude=$lat&access_token=sk.eyJ1IjoiYmpvaG9sbW0iLCJhIjoiY2x0eWVwZHp5MGRmaTJrcGpueG8zcTR1MCJ9.zal9bJ3fdxMij0MJB-GvUQ"
            val callReverseGeoCode = client.get(clickURL)

            return callReverseGeoCode.body<GeocodingPlacesResponse>().features[0].properties
        }

        catch (e: Exception){

        }
        return null

    }

    suspend fun searchGeoCode(searchString: String): GeocodingPlacesResponse? {
        try{
            val searchURL = "https://api.mapbox.com/search/geocode/v6/forward?q=$searchString&country=no&proximity=ip&access_token=sk.eyJ1IjoiYmpvaG9sbW0iLCJhIjoiY2x0eWVwZHp5MGRmaTJrcGpueG8zcTR1MCJ9.zal9bJ3fdxMij0MJB-GvUQ"
            val callSearchGeoCode = client.get(searchURL)
            print(callSearchGeoCode)
            print(callSearchGeoCode.body<GeocodingPlacesResponse>())
            return callSearchGeoCode.body<GeocodingPlacesResponse>()
        }

        catch (e: Exception){



        }
        return null

    }
}