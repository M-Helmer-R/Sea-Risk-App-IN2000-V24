package no.uio.ifi.in2000.testgit.data

import no.uio.ifi.in2000.testgit.ui.Activity.NowCastUIState
import no.uio.ifi.in2000.testgit.ui.map.OceanForeCastUIState
import kotlin.math.roundToInt


// maa denne vaere suspend for aa sikre at den ikke fryser resten av appen
suspend fun padleAlgoritme(oceanForeCastUIState: OceanForeCastUIState, nowCastUIState: NowCastUIState): Int {
    // How much weight each variable gives to the end result
    // Sum should be 1.0
    val windSpeedWeight = 0.4
    val waveHeightWeight = 0.3
    val airTempWeight = 0.15
    val waterTempWeight = 0.15

    //Weather variables
    val oceanTemp = oceanForeCastUIState.oceanDetails?.seaWaterTemperature

    val airTemp = nowCastUIState.nowCastData?.airTemperature

    val windSpeed = nowCastUIState.nowCastData?.windSpeed

    val waveHeight = oceanForeCastUIState.oceanDetails?.seaSurfaceWaveHeight

    // swimming in too hot water can cause dehydration, hence the OR in the last two calculations
    val waterTempResult = when  {
        (oceanTemp !!>= 20) && (oceanTemp < 30) -> 100.0 * waterTempWeight
        (oceanTemp >= 17) && (oceanTemp < 20) -> 75.0 * waterTempWeight
        ((oceanTemp >= 15) && (oceanTemp < 17)) || ((oceanTemp >= 30) && (oceanTemp < 33)) -> 50.0 * waterTempWeight
        ((oceanTemp >= 10) && (oceanTemp < 15)) || (oceanTemp >= 33) -> 25.0 * waterTempWeight
        else -> 0.0
    }

    val airTempResult = when  {
        (airTemp !! >= 25) -> 100.0 * airTempWeight
        (airTemp  >= 20) && (airTemp  < 25) -> 75.0 * airTempWeight
        (airTemp  >= 15) && (airTemp  < 20) -> 50.0 * airTempWeight
        (airTemp  >= 10) && (airTemp  < 15) -> 25.0 * airTempWeight
        else -> 0.0
    }

    // calculates a value for wind speed
    val windResult = when  {
        (windSpeed !!>= 0) && (windSpeed < 3.4) -> 100.0 * windSpeedWeight
        (windSpeed >= 3.4) && (windSpeed < 5.5) -> 75.0 * windSpeedWeight
        (windSpeed >= 5.5) && (windSpeed < 8) -> 50.0 * windSpeedWeight
        (windSpeed >= 8) && (windSpeed < 10.7) -> 25.0 * windSpeedWeight
        else -> 0.0
    }

    // calculates a value for wave height
    val waveResult = when {
        (waveHeight !! >= 0) && (waveHeight < 0.4) -> 100.0 * waveHeightWeight
        (waveHeight >= 0.4) && (waveHeight < 0.85) -> 75.0 * waveHeightWeight
        (waveHeight >= 0.85) && (waveHeight < 1.26) -> 50.0 * waveHeightWeight
        (waveHeight >= 1.26) && (waveHeight < 2.5) -> 25.0 * waveHeightWeight
        else -> 0.0
    }

    // returns 0 if one of the values is outside acceptable parameters
    return if (
        windResult == 0.0 ||
        waveResult == 0.0 ||
        waterTempResult == 0.0 ||
        airTempResult == 0.0
        ){
        0
    } else
        ((windResult + waveResult + waterTempResult + airTempResult)/10).roundToInt()
}