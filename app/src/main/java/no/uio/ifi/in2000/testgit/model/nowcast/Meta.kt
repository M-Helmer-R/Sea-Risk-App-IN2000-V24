package com.example.example

import com.google.gson.annotations.SerializedName


data class Meta (

  @SerializedName("updated_at"     ) var updatedAt     : String? = null,
  //@SerializedName("units"          ) var units         : Units?  = Units(),
  @SerializedName("radar_coverage" ) var radarCoverage : String? = null

)