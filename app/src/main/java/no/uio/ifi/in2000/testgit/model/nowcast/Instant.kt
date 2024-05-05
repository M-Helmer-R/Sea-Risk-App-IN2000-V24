package no.uio.ifi.in2000.testgit.model.nowcast

import com.google.gson.annotations.SerializedName
import no.uio.ifi.in2000.testgit.model.nowcast.Details


data class Instant (

  @SerializedName("details" ) val details : Details? = Details()

)