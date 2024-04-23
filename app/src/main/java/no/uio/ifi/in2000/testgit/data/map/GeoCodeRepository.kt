package no.uio.ifi.in2000.testgit.data.map

class GeoCodeRepository {
    val geoCodeDataSource: GeoCodeDataSource = GeoCodeDataSource()

    fun reverseGeoCode(lon: Double, lat: Double, callback: ReverseGeocodeCallback) {

        geoCodeDataSource.reverseGeoCode(lon, lat, object: ReverseGeocodeCallback{
            override fun onSuccess(placeName: String){
                callback.onSuccess(placeName)
            }

            override fun onFailure(placeName: String){
                callback.onFailure(placeName)
            }
        })


    }
}