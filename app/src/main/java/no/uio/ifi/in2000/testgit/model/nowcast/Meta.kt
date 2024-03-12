package com.example.example

import com.google.gson.annotations.SerializedName


data class Meta (

  @SerializedName("updated_at"     ) val updatedAt     : String? = null,
  //@SerializedName("units"          ) val units         : Units?  = Units(),
  @SerializedName("radar_coverage" ) val radarCoverage : String? = null

)