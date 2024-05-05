package no.uio.ifi.in2000.testgit.model.nowcast

import com.google.gson.annotations.SerializedName


data class Summary (

  @SerializedName("symbol_code" ) val symbolCode : String? = null

)