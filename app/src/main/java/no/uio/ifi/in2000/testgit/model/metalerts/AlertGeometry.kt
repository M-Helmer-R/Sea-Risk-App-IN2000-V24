package com.example.example

import com.google.gson.annotations.SerializedName


data class AlertGeometry (
  @SerializedName("coordinates") val coordinates: List<List<Any>>,
  @SerializedName("type"        ) val type        : String? = null

)
