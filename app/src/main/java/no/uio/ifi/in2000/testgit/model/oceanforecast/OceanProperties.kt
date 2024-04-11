package no.uio.ifi.in2000.testgit.model.oceanforecast

import com.google.gson.annotations.SerializedName


data class OceanProperties (

    @SerializedName("meta"       ) var meta       : OceanMeta?                 = OceanMeta(),
    @SerializedName("timeseries" ) var timeseries : ArrayList<OceanTimeseries> = arrayListOf()

)