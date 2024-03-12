package com.example.example

import com.google.gson.annotations.SerializedName


data class AlertFeatures (

  @SerializedName("geometry"   ) val alertGeometry   : AlertGeometry? ,
  @SerializedName("properties" ) val alertProperties : AlertProperties? ,
  @SerializedName("type"       ) val type       : String?     = null,
  @SerializedName("when"       ) val whe       : When?

)