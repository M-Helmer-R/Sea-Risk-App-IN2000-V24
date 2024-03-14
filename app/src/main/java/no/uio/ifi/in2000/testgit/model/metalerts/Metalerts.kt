package com.example.example

import com.google.gson.annotations.SerializedName


data class Metalerts (

    // må kanskje være nullable
    @SerializedName("features"   ) val features   : List<AlertFeatures>?,
    @SerializedName("lang"       ) val lang       : String?             = null,
    @SerializedName("lastChange" ) val lastChange : String?             = null,
    @SerializedName("type"       ) val type       : String?             = null

)