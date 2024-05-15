package no.uio.ifi.in2000.testgit.data

import android.util.Log
import no.uio.ifi.in2000.testgit.ui.Activity.NowCastUIState
import no.uio.ifi.in2000.testgit.ui.map.OceanForeCastUIState
import kotlin.math.roundToInt

// Define a data class to represent temperature limits

suspend fun seileAlgoritme(oceanForeCastUIState: OceanForeCastUIState, nowCastUIState: NowCastUIState): Int {
    // How much weight each variable gives to the end result
    // Sum should be 1.0
    val windSpeedWeight = 0.6
    val waveHeightWeight = 0.2
    val currentWeight = 0.2


    val windSpeed = nowCastUIState.nowCastData?.windSpeed
    val waveHeight = oceanForeCastUIState.oceanDetails?.seaSurfaceWaveHeight
    val currentSpeed = oceanForeCastUIState.oceanDetails?.seaWaterSpeed

    // Create lists of temperature limits using the defined data class
    val windSpeeds = listOf(
        WeatherLimit(Double.NEGATIVE_INFINITY..1.0, 0.0), // near impossible with sailing
        WeatherLimit(1.0..2.5, 25.0), // wind struggles to fill sail, bad conditions
        WeatherLimit(2.5..5.0, 60.0), // possible but not ideal
        WeatherLimit(5.0..7.0, 85.0), // calm and easy sailing, good for beginners
        WeatherLimit(7.0..12.0, 100.0), // engaging and fun wind speeds for sailing
        WeatherLimit(12.0..15.0, 70.0), // only fitting for experienced sailors
        WeatherLimit(15.0..17.0, 50.0), // careful preparation and caution needed
        WeatherLimit(17.0..Double.POSITIVE_INFINITY, 0.0) // Possibly dangerous levels of wind

    )

    val waveHeights = listOf(
        WeatherLimit(0.0..0.4, 100.0),
        WeatherLimit(0.4..0.85, 75.0),
        WeatherLimit(0.85..1.26, 50.0),
        WeatherLimit(1.26..2.5, 25.0),
        WeatherLimit(2.5..Double.POSITIVE_INFINITY, 0.0)
    )

    val currentSpeeds = listOf(
        WeatherLimit(0.0..0.26, 80.0),
        WeatherLimit(0.26..0.5, 100.0),
        WeatherLimit(0.5..1.0, 80.0),
        WeatherLimit(1.0..2.0, 60.0),
        WeatherLimit(2.0..Double.POSITIVE_INFINITY, 0.0)
    )


    val waveHeightResult = calculateRiskLevel(waveHeightWeight, waveHeight!!, waveHeights)
    val windSpeedResult = calculateRiskLevel(windSpeedWeight, windSpeed!!, windSpeeds)
    val currentSpeedResult = calculateRiskLevel(currentWeight, currentSpeed!!, currentSpeeds)
    Log.i("seileAlgoritme", "wind: $windSpeedResult\")\n" +
            "\"wave: $waveHeightResult\")\n" +
            "\"current: $currentSpeedResult"
    )

    // returns 0 if one of the values is outside acceptable parameters
    return if (
        windSpeedResult == 0.0 ||
        waveHeightResult == 0.0 ||
        currentSpeedResult == 0.0
    ){
        0
    } else
        ((windSpeedResult + waveHeightResult + currentSpeedResult)/10).roundToInt()
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