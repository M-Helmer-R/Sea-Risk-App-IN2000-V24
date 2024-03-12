package com.example.example

import com.google.gson.annotations.SerializedName


data class Instant (

  @SerializedName("details" ) val details : Details? = Details()

)