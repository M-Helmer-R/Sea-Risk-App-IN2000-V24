package com.example.example

import com.google.gson.annotations.SerializedName


data class Geometry (

  @SerializedName("type"        ) val type        : String?           = null,
  @SerializedName("coordinates" ) val coordinates : ArrayList<Double> = arrayListOf()

)