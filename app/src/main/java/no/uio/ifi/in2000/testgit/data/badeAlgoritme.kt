package no.uio.ifi.in2000.testgit.data

import android.util.Log
import no.uio.ifi.in2000.testgit.ui.Activity.NowCastUIState
import no.uio.ifi.in2000.testgit.ui.map.OceanForeCastUIState
import kotlin.math.roundToInt

// Define a data class to represent temperature limits


suspend fun badeAlgoritme(oceanForeCastUIState: OceanForeCastUIState, nowCastUIState: NowCastUIState): Int {
    // How much weight each variable gives to the end result
    // Sum should be 1.0
    val windSpeedWeight = 0.1
    val airTempWeight = 0.4
    val waterTempWeight = 0.5

    //Weather variables
    //how can i cast them from Double? to Double so that i dont get an error further down
    val oceanTemp = oceanForeCastUIState.oceanDetails?.seaWaterTemperature
    val airTemp = nowCastUIState.nowCastData?.airTemperature
    val windSpeed = nowCastUIState.nowCastData?.windSpeed

    // Create lists of temperature limits using the defined data class
    val oceanTemps = listOf(
        WeatherLimit(Double.NEGATIVE_INFINITY..0.0, 0.0), // possibility of ice on water
        WeatherLimit(0.0..2.0, 10.0), // planning and experience very important
        WeatherLimit(2.0..7.0, 30.0),
        WeatherLimit(7.0..12.0, 55.0),
        WeatherLimit(12.0..15.0, 80.0),
        WeatherLimit(15.0..30.0, 100.0),
        WeatherLimit(30.0..33.0, 85.0), // higher risk of dehydration
        WeatherLimit(33.0..Double.POSITIVE_INFINITY, 75.0) // higher risk of dehydration
    )

    val airTemps = listOf(
        WeatherLimit(Double.NEGATIVE_INFINITY..10.0, 0.0),
        WeatherLimit(10.0..15.0, 25.0),
        WeatherLimit(15.0..20.0, 50.0),
        WeatherLimit(20.0..25.0, 75.0),
        WeatherLimit(25.0..Double.POSITIVE_INFINITY, 100.0)
    )

    val windSpeeds = listOf(
        WeatherLimit(0.0..3.4, 100.0),
        WeatherLimit(3.4..5.5, 75.0),
        WeatherLimit(5.5..8.0, 50.0),
        WeatherLimit(8.0..10.7, 25.0),
        WeatherLimit(10.7..Double.POSITIVE_INFINITY, 0.0)
    )


    val airTempResult = calculateRiskLevel(airTempWeight, airTemp!!, airTemps)
    val waterTempResult = calculateRiskLevel(waterTempWeight, oceanTemp!!, oceanTemps)
    val windSpeedResult = calculateRiskLevel(windSpeedWeight, windSpeed!!, windSpeeds)

    Log.i("badeAlgoritme", "wind: $windSpeedResult\")\n" +
            "\"water: $waterTempResult\"\n" +
            "\"air: $airTempResult\"")
    // returns 0 if one of the values is outside acceptable parameters
    return if (
        windSpeedResult == 0.0 ||
        waterTempResult == 0.0 ||
        airTempResult == 0.0
    ){
        0
    } else
        ((windSpeedResult + waterTempResult + airTempResult)/10).roundToInt()
}

private suspend fun calculateRiskLevel(weight: Double, weatherInput: Double, limits: List<WeatherLimit>): Double {
    var riskLevel = 0.0
    for (limit in limits){
        if(weatherInput in limit.range){
            riskLevel += limit.value
            // break is neccessary so that other intervals arent checked
            break
        }
    }
    return riskLevel*weight
}