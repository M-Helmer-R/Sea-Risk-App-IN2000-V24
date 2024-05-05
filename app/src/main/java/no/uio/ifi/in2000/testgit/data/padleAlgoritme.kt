package no.uio.ifi.in2000.testgit.data


// maa denne vaere suspend for aa sikre at den ikke fryser resten av appen
fun padleAlgoritme(by: By, wind: Double, wave: Double): Double {
    // How much weight each variable gives to the end result
    // Sum should be 1.0
    val windSpeedWeight = 0.4
    val waveHeightWeight = 0.3
    val airTempWeight = 0.15
    val waterTempWeight = 0.15

    // swimming in too hot water can cause dehydration, hence the OR in the last two calculations
    val waterTempResult = when  {
        (by.waterTemp >= 20) && (by.waterTemp < 30) -> 100.0 * waterTempWeight
        (by.waterTemp >= 17) && (by.waterTemp < 20) -> 75.0 * waterTempWeight
        ((by.waterTemp >= 15) && (by.waterTemp < 17)) || ((by.waterTemp >= 30) && (by.waterTemp < 33)) -> 50.0 * waterTempWeight
        ((by.waterTemp >= 10) && (by.waterTemp < 15)) || (by.waterTemp >= 33) -> 25.0 * waterTempWeight
        else -> 0.0
    }

    val airTempResult = when  {
        (by.airTemp >= 25) -> 100.0 * airTempWeight
        (by.airTemp >= 20) && (by.airTemp < 25) -> 75.0 * airTempWeight
        (by.airTemp >= 15) && (by.airTemp < 20) -> 50.0 * airTempWeight
        (by.airTemp >= 10) && (by.airTemp < 15) -> 25.0 * airTempWeight
        else -> 0.0
    }

    // calculates a value for wind speed
    val windResult = when  {
        (wind >= 0) && (wind < 3.4) -> 100.0 * windSpeedWeight
        (wind >= 3.4) && (wind < 5.5) -> 75.0 * windSpeedWeight
        (wind >= 5.5) && (wind < 8) -> 50.0 * windSpeedWeight
        (wind >= 8) && (wind < 10.7) -> 25.0 * windSpeedWeight
        else -> 0.0
    }

    // calculates a value for wave height
    val waveResult = when {
        (wave >= 0) && (wave < 0.4) -> 100.0 * waveHeightWeight
        (wave >= 0.4) && (wave < 0.85) -> 75.0 * waveHeightWeight
        (wave >= 0.85) && (wave < 1.26) -> 50.0 * waveHeightWeight
        (wave >= 1.26) && (wave < 2.5) -> 25.0 * waveHeightWeight
        else -> 0.0
    }

    // returns 0 if one of the values is outside acceptable parameters
    return if (
        windResult == 0.0 ||
        waveResult == 0.0 ||
        waterTempResult == 0.0 ||
        airTempResult == 0.0
        ){
        0.0
    } else
        windResult + waveResult + waterTempResult + airTempResult
}