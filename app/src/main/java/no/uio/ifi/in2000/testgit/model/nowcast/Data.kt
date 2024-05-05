package no.uio.ifi.in2000.testgit.model.nowcast

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("instant"      ) val instant    : Instant?    = Instant(),
  @SerializedName("next_1_hours" ) val next1Hours : Next1Hours? = Next1Hours()

)