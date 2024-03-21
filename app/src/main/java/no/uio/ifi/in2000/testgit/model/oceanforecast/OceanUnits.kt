package no.uio.ifi.in2000.testgit.model.oceanforecast

import com.google.gson.annotations.SerializedName


data class OceanUnits (

  @SerializedName("sea_surface_wave_from_direction" ) var seaSurfaceWaveFromDirection : String? = null,
  @SerializedName("sea_surface_wave_height"         ) var seaSurfaceWaveHeight        : String? = null,
  @SerializedName("sea_water_speed"                 ) var seaWaterSpeed               : String? = null,
  @SerializedName("sea_water_temperature"           ) var seaWaterTemperature         : String? = null,
  @SerializedName("sea_water_to_direction"          ) var seaWaterToDirection         : String? = null

)