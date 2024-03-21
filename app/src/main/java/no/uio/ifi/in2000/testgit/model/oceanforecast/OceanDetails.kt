package no.uio.ifi.in2000.testgit.model.oceanforecast

import com.google.gson.annotations.SerializedName


data class OceanDetails (

  @SerializedName("sea_surface_wave_from_direction" ) var seaSurfaceWaveFromDirection : Int?    = null,
  @SerializedName("sea_surface_wave_height"         ) var seaSurfaceWaveHeight        : Double? = null,
  @SerializedName("sea_water_speed"                 ) var seaWaterSpeed               : Double? = null,
  @SerializedName("sea_water_temperature"           ) var seaWaterTemperature         : Double? = null,
  @SerializedName("sea_water_to_direction"          ) var seaWaterToDirection         : Double? = null

)