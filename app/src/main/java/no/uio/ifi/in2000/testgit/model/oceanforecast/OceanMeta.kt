package no.uio.ifi.in2000.testgit.model.oceanforecast

import com.google.gson.annotations.SerializedName


data class OceanMeta (

  @SerializedName("updated_at" ) var updatedAt : String? = null,
  @SerializedName("units"      ) var units     : OceanUnits?  = OceanUnits()

)