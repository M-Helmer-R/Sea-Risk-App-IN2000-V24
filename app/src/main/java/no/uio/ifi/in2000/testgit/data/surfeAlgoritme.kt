package no.uio.ifi.in2000.testgit.data

import android.util.Log
import no.uio.ifi.in2000.testgit.ui.Activity.NowCastUIState
import no.uio.ifi.in2000.testgit.ui.map.OceanForeCastUIState
import kotlin.math.roundToInt



/*

 */
suspend fun surfeAlgoritme(oceanForeCastUIState: OceanForeCastUIState, nowCastUIState: NowCastUIState): Int {
    // Define the weights for each parameter
    // Wind speed weight set to 0.3, this
    // will be subtracted from sum of wave height and water temp
    val windSpeedWeight = 0.3
    // waveHeight and water temp sum to 1.0
    val waveHeightWeight = 0.8
    val waterTempWeight = 0.2

    // Get the values from the UI states
    val oceanTemp = oceanForeCastUIState.oceanDetails?.seaWaterTemperature
    val windSpeed = nowCastUIState.nowCastData?.windSpeed
    val waveHeight = oceanForeCastUIState.oceanDetails?.seaSurfaceWaveHeight



    // Create lists of temperature limits using the defined data class
    // Score of 0.0 is for bad conditions, 100.0 is for good conditions
    val oceanTemps = listOf(
        WeatherLimit(Double.NEGATIVE_INFINITY..0.0, 0.0), // possibility of ice on water
        WeatherLimit(0.0..2.0, 10.0), // planning and experience very important
        WeatherLimit(2.0..7.0, 30.0), // dry-suit recommended
        WeatherLimit(7.0..12.0, 55.0), // wetsuit recommended
        WeatherLimit(12.0..15.0, 80.0),
        WeatherLimit(15.0..30.0, 100.0),
        WeatherLimit(30.0..33.0, 85.0), // higher risk of dehydration
        WeatherLimit(33.0..Double.POSITIVE_INFINITY, 75.0) // higher risk of dehydration
    )

    // Create lists of wind speed limits using the defined data class
    // This parameter will be subtracted from total surf score
    // Score of 0.0 is for good conditions, 100.0 is for bad conditions
    val windSpeeds = listOf(
        WeatherLimit(0.0..2.7, 0.0), // little to no winds, perfect wind conditions
        WeatherLimit(2.7..5.5, 20.0), // little to moderate winds, good conditions
        WeatherLimit(5.5..8.3, 50.0), // moderate to strong winds, moderate conditions
        WeatherLimit(8.0..11.1, 75.0), // strong winds, bad conditions
        WeatherLimit(11.1..Double.POSITIVE_INFINITY, 100.0) // avoid surfing in these conditions
    )

    // Create lists of wave height limits using the defined data class
    // Score of 0.0 is for bad conditions, 100.0 is for good conditions
    val waveHeights = listOf(
        WeatherLimit(0.0..0.3, 0.0),
        WeatherLimit(0.3..0.6, 30.0),
        WeatherLimit(0.6..0.9, 50.0),
        WeatherLimit(0.9..1.2, 65.0),
        WeatherLimit(1.2..1.5, 75.0),
        WeatherLimit(1.5..1.8, 85.0),
        WeatherLimit(1.8..3.0, 100.0),
        WeatherLimit(3.0..6.0, 50.0), // all heights above 3m is dependent on experience
        WeatherLimit(6.0..Double.POSITIVE_INFINITY, 0.0) // over 6m can be dangerous for all skill levels
    )

    // Calculate the risk level for each parameter
    // Using !! to handle nullable values, these are checked before calling this function
    val waterTempResult = calculateRiskLevel(waterTempWeight, oceanTemp!!, oceanTemps)
    val waveHeightResult = calculateRiskLevel(waveHeightWeight, waveHeight!!, waveHeights)
    val windSpeedResult = calculateRiskLevel(windSpeedWeight, windSpeed!!, windSpeeds)



    // Return the risk level, if any are out of range --> return 0.0
    return if (
        windSpeedResult == 30.0 ||
        waveHeightResult == 0.0 ||
        waterTempResult == 0.0
    ){
        0
    } else
        ((waveHeightResult + waterTempResult - windSpeedResult)/10).roundToInt()
}

// Helper function to calculate the risk level for a given parameter
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