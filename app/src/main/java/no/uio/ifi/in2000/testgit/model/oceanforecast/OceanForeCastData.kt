package no.uio.ifi.in2000.testgit.model.oceanforecast


import com.google.gson.annotations.SerializedName


data class OceanForeCastData (

    @SerializedName("type"       ) var type       : String?     = null,
    //@SerializedName("geometry"   ) var geometry   : Geometry?   = Geometry(),
    @SerializedName("properties" ) var properties : OceanProperties? = OceanProperties()

)