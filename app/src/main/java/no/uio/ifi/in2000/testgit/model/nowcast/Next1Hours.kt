package no.uio.ifi.in2000.testgit.model.nowcast

import com.google.gson.annotations.SerializedName


data class Next1Hours (

  @SerializedName("summary" ) val summary : Summary? = Summary(),
  @SerializedName("details" ) val details : Details? = Details()

)