package com.example.example

import com.google.gson.annotations.SerializedName


data class Details (
    @SerializedName("air_temperature"      ) var airTemperature      : Double? = null,
    @SerializedName("precipitation_amount" ) var precipitationAmount : Double? = null,
    @SerializedName("precipitation_rate"   ) var precipitationRate   : Double? = null,
    @SerializedName("relative_humidity"    ) var relativeHumidity    : Double? = null,
    @SerializedName("wind_from_direction"  ) var windFromDirection   : Double? = null,
    @SerializedName("wind_speed"           ) var windSpeed           : Double? = null,
    @SerializedName("wind_speed_of_gust"   ) var windSpeedOfGust     : Double? = null

)