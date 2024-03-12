package com.example.example

import com.google.gson.annotations.SerializedName


data class AlertGeometry (

  @SerializedName("coordinates" ) val coordinates : List<List<List<Double>>>,
  @SerializedName("type"        ) val type        : String?                                 = null

)