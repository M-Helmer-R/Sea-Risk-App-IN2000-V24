package no.uio.ifi.in2000.testgit.model.nowcast

import com.google.gson.annotations.SerializedName


data class NowcastData (

  @SerializedName("type"       ) var type       : String?     = null,
  //@SerializedName("geometry"   ) var geometry   : Geometry?   = Geometry(),
  @SerializedName("properties" ) var Properties : Properties? = Properties()

)