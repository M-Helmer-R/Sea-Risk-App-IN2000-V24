package no.uio.ifi.in2000.testgit.model.nowcast

import com.google.gson.annotations.SerializedName
import no.uio.ifi.in2000.testgit.model.nowcast.Data


data class Timeseries (

  @SerializedName("time" ) val time : String? = null,
  @SerializedName("data" ) val data : Data?   = Data()

)