package com.example.example

import com.google.gson.annotations.SerializedName


data class Units (

  @SerializedName("air_temperature"      ) val airTemperature      : String? = null,
  @SerializedName("precipitation_amount" ) val precipitationAmount : String? = null,
  @SerializedName("precipitation_rate"   ) val precipitationRate   : String? = null,
  @SerializedName("relative_humidity"    ) val relativeHumidity    : String? = null,
  @SerializedName("wind_from_direction"  ) val windFromDirection   : String? = null,
  @SerializedName("wind_speed"           ) val windSpeed           : String? = null,
  @SerializedName("wind_speed_of_gust"   ) val windSpeedOfGust     : String? = null

)