package com.example.example

import com.google.gson.annotations.SerializedName


data class Next1Hours (

  @SerializedName("summary" ) val summary : Summary? = Summary(),
  @SerializedName("details" ) val details : Details? = Details()

)