package no.uio.ifi.in2000.testgit.data.map

import android.util.Log

class GeoCodeRepository {
    val geoCodeDataSource: GeoCodeDataSource = GeoCodeDataSource()


    suspend fun reverseGeoCode2(lon: Double, lat: Double): Properties? {
        return geoCodeDataSource.reverseGeoCode2(lon, lat)
    }


    suspend fun searchGeoCode(searchString: String): GeocodingPlacesResponse? {
        Log.i("GeoCodeRepository", "searchGeoCode called")
        return geoCodeDataSource.searchGeoCode(searchString)

    }
}