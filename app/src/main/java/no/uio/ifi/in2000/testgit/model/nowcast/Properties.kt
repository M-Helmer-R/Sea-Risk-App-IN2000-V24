package com.example.example

import com.google.gson.annotations.SerializedName


data class Properties (

  @SerializedName("meta"       ) val meta       : Meta?                 = Meta(),
  @SerializedName("timeseries" ) val timeseries : ArrayList<Timeseries> = arrayListOf(),

)