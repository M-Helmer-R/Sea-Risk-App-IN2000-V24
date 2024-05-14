package no.uio.ifi.in2000.testgit.data

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

    //Weather variables
    //how can i cast them from Double? to Double so that i dont get an error further down
    val oceanTemp = oceanForeCastUIState.oceanDetails?.seaWaterTemperature
    val airTemp = nowCastUIState.nowCastData?.airTemperature
    val windSpeed = nowCastUIState.nowCastData?.windSpeed
    val waveHeight = oceanForeCastUIState.oceanDetails?.seaSurfaceWaveHeight
    val currentSpeed = oceanForeCastUIState.oceanDetails?.seaWaterSpeed

    // Create lists of temperature limits using the defined data class
    val windSpeeds = listOf(
        WeatherLimit(0.0..3.4, 100.0),
        WeatherLimit(3.4..5.5, 75.0),
        WeatherLimit(5.5..8.0, 50.0),
        WeatherLimit(8.0..10.7, 25.0),
        WeatherLimit(10.7..Double.POSITIVE_INFINITY, 0.0)
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