package no.uio.ifi.in2000.testgit.data

fun surfeAlgoritme(by: By): Double {
    // How much weight each variable gives to the end result
    // Sum should be 1.0
    val waterTempWeight = 0.1
    val airTempWeight = 0.1
    val waveHeightWeight = 0.5
    val windSpeedWeight = 0.2
    // seconds between each wave


    //UPDATE THE VALUES HERE
    val waveHeightResult = when  {
        (by.waterTemp >= 20) && (by.waterTemp < 30) -> 100.0 * waveHeightWeight
        (by.waterTemp >= 17) && (by.waterTemp < 20) -> 75.0 * waveHeightWeight
        ((by.waterTemp >= 15) && (by.waterTemp < 17)) || ((by.waterTemp >= 30) && (by.waterTemp < 33)) -> 50.0 * waveHeightWeight
        ((by.waterTemp >= 10) && (by.waterTemp < 15)) || (by.waterTemp >= 33) -> 25.0 * waveHeightWeight
        else -> 0.0
    }


    // UPDATE THE VALUES HERE
    val windSpeedResult = when  {
        (by.waterTemp >= 20) && (by.waterTemp < 30) -> 100.0 * windSpeedWeight
        (by.waterTemp >= 17) && (by.waterTemp < 20) -> 75.0 * windSpeedWeight
        ((by.waterTemp >= 15) && (by.waterTemp < 17)) || ((by.waterTemp >= 30) && (by.waterTemp < 33)) -> 50.0 * windSpeedWeight
        ((by.waterTemp >= 10) && (by.waterTemp < 15)) || (by.waterTemp >= 33) -> 25.0 * windSpeedWeight
        else -> 0.0
    }

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

    // returns 0 if one of the values is outside acceptable parameters
    return if (
        waterTempResult == 0.0 ||
        airTempResult == 0.0 ||
        waveHeightResult == 0.0 ||
        windSpeedResult == 0.0
        ){
        0.0
    }
    // adding the results to return a value between 1 and 100
    else
        waterTempResult + airTempResult
}