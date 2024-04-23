package no.uio.ifi.in2000.testgit.model.oceanforecast

import com.google.gson.annotations.SerializedName


data class OceanInstant (

  @SerializedName("details" ) var details : OceanDetails? = OceanDetails()

)