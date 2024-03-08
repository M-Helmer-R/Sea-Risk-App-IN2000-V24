package com.example.example

import com.google.gson.annotations.SerializedName


data class Units (

  @SerializedName("air_temperature"      ) var airTemperature      : String? = null,
  @SerializedName("precipitation_amount" ) var precipitationAmount : String? = null,
  @SerializedName("precipitation_rate"   ) var precipitationRate   : String? = null,
  @SerializedName("relative_humidity"    ) var relativeHumidity    : String? = null,
  @SerializedName("wind_from_direction"  ) var windFromDirection   : String? = null,
  @SerializedName("wind_speed"           ) var windSpeed           : String? = null,
  @SerializedName("wind_speed_of_gust"   ) var windSpeedOfGust     : String? = null

)