package no.uio.ifi.in2000.testgit.data.map

import com.google.gson.annotations.SerializedName

data class GeocodingPlacesResponse(
    //@SerializedName("type") val type: String,
    @SerializedName("features") val features: List<LocationAttributes>
)

/*
data class LocationAttributes(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("place_type") val place_type: List<String>,
    @SerializedName("relevance") val relevance: Double,
    @SerializedName("properties") val properties: Properties,
    @SerializedName("text") val text: String,
    @SerializedName("place_name") val place_name: String,
    @SerializedName("bbox") val bbox: List<String>,
    @SerializedName("center") val center: List<String>,
    @SerializedName("geometry") val geometry: Geometry,
    @SerializedName("context") val context: List<Context>

)
*/

data class LocationAttributes(
    @SerializedName("properties") val properties: Properties,


)


data class Properties(
    @SerializedName("mapbox_id") val mapbox_id: String,
    @SerializedName("wikidata") val wikidata: String,
    @SerializedName("name_preferred") val name: String,
    @SerializedName("coordinates") val coordinates: Coordinates
)

data class Coordinates(
    @SerializedName("longitude") val lon: Double,
    @SerializedName("latitude") val lat: Double
)
data class Geometry(
    @SerializedName("type") val type: String,
    @SerializedName("coordinates") val coordinates: List<String>
)

data class Context(
    @SerializedName("id") val id: String,
    @SerializedName("mapbox_id") val mapbox_id: String,
    @SerializedName("wikidata") val wikidata: String,
    @SerializedName("text") val text: String,
    @SerializedName("short_code") val short_code: String? = null
)