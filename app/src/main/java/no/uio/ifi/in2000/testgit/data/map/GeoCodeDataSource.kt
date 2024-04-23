package no.uio.ifi.in2000.testgit.data.map

import android.util.Log
import com.mapbox.api.geocoding.v5.GeocodingCriteria
import com.mapbox.api.geocoding.v5.MapboxGeocoding
import com.mapbox.api.geocoding.v5.models.GeocodingResponse
import com.mapbox.geojson.Point
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface ReverseGeocodeCallback {
    fun onSuccess(placeName: String)
    fun onFailure(placeName: String)
}
class GeoCodeDataSource {
    fun reverseGeoCode(lon: Double, lat: Double, callback: ReverseGeocodeCallback) {
        var placeNameDisplay = "Test"
        val reverseGeocode = MapboxGeocoding.builder()
            .accessToken("sk.eyJ1IjoiYmpvaG9sbW0iLCJhIjoiY2x0eWVwZHp5MGRmaTJrcGpueG8zcTR1MCJ9.zal9bJ3fdxMij0MJB-GvUQ")
            .query(Point.fromLngLat(lon, lat))
            //.geocodingTypes(GeocodingCriteria.TYPE_ADDRESS)
            .build()


        reverseGeocode.enqueueCall(object : Callback<GeocodingResponse> {
            override fun onResponse(call: Call<GeocodingResponse>, response: Response<GeocodingResponse>) {
                val results = response.body()?.features()
                if (results != null && results.size > 0) {
                    val firstResult = results[0]
                    val placeName = firstResult.placeName()
                    if (placeName != null) {
                        callback.onSuccess(placeName)
                    }
                    //isDialogVisible = true
                    Log.i("Geocoding", "Place name: $placeName")
                } else {
                    callback.onFailure("Ingen Data")
                    Log.i("Geocoding", "No results found.")
                }
            }

            override fun onFailure(call: Call<GeocodingResponse>, throwable: Throwable) {
                Log.e("Geocoding", "Error: " + throwable.message)
            }

        })
    }
}