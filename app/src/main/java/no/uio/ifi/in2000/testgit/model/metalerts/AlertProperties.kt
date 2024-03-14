package com.example.example

import com.google.gson.annotations.SerializedName


data class AlertProperties (

  @SerializedName("MunicipalityId"       ) val MunicipalityId       : String?              = null,
  @SerializedName("administrativeId"     ) val administrativeId     : String?              = null,
  @SerializedName("area"                 ) val area                 : String?              = null,
  @SerializedName("awarenessResponse"    ) val awarenessResponse    : String?              = null,
  @SerializedName("awarenessSeriousness" ) val awarenessSeriousness : String?              = null,
  @SerializedName("awareness_level"      ) val awarenessLevel       : String?              = null,
  @SerializedName("awareness_type"       ) val awarenessType        : String?              = null,
  @SerializedName("certainty"            ) val certainty            : String?              = null,
  @SerializedName("consequences"         ) val consequences         : String?              = null,
  @SerializedName("county"               ) val county               : ArrayList<String>    = arrayListOf(),
  @SerializedName("description"          ) val description          : String?              = null,
  @SerializedName("event"                ) val event                : String?              = null,
  @SerializedName("eventAwarenessName"   ) val eventAwarenessName   : String?              = null,
  @SerializedName("eventEndingTime"      ) val eventEndingTime      : String?              = null,
  @SerializedName("geographicDomain"     ) val geographicDomain     : String?              = null,
  @SerializedName("id"                   ) val id                   : String?              = null,
  @SerializedName("instruction"          ) val instruction          : String?              = null,
  @SerializedName("resources"            ) val resources            : ArrayList<Resources> = arrayListOf(),
  @SerializedName("riskMatrixColor"      ) val riskMatrixColor      : String?              = null,
  @SerializedName("severity"             ) val severity             : String?              = null,
  @SerializedName("title"                ) val title                : String?              = null,
  @SerializedName("type"                 ) val type                 : String?              = null

)