package no.uio.ifi.in2000.testgit.model.oceanforecast

import com.google.gson.annotations.SerializedName


data class OceanTimeseries (

  @SerializedName("time" ) var time : String? = null,
  @SerializedName("data" ) var data : OceanData?   = OceanData()

)