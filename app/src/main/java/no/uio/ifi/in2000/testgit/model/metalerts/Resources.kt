package com.example.example

import com.google.gson.annotations.SerializedName


data class Resources (

  @SerializedName("description" ) val description : String? = null,
  @SerializedName("mimeType"    ) val mimeType    : String? = null,
  @SerializedName("uri"         ) val uri         : String? = null

)