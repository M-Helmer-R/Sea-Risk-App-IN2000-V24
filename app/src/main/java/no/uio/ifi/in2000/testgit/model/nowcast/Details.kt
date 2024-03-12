package com.example.example

import com.google.gson.annotations.SerializedName


data class Details (
    @SerializedName("air_temperature"      ) val airTemperature      : Double? = null,
    @SerializedName("precipitation_amount" ) val precipitationAmount : Double? = null,
    @SerializedName("precipitation_rate"   ) val precipitationRate   : Double? = null,
    @SerializedName("relative_humidity"    ) val relativeHumidity    : Double? = null,
    @SerializedName("wind_from_direction"  ) val windFromDirection   : Double? = null,
    @SerializedName("wind_speed"           ) val windSpeed           : Double? = null,
    @SerializedName("wind_speed_of_gust"   ) val windSpeedOfGust     : Double? = null

)