package com.example.example

import com.google.gson.annotations.SerializedName


data class Timeseries (

  @SerializedName("time" ) val time : String? = null,
  @SerializedName("data" ) val data : Data?   = Data()

)