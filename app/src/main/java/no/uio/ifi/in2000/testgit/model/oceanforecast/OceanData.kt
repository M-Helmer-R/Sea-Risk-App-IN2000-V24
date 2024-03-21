package no.uio.ifi.in2000.testgit.model.oceanforecast

import com.google.gson.annotations.SerializedName


data class OceanData (

  @SerializedName("instant" ) var instant : OceanInstant? = OceanInstant()

)