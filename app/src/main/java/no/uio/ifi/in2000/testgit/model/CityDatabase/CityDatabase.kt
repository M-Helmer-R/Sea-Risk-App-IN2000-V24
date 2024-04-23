package no.uio.ifi.in2000.testgit.model.CityDatabase

enum class CityDatabase(val cityName: String, val lon: String, val lat: String) {
    OSLO("Oslo", "10.7461", "59.9127"),
    Bergen("Bergen", "5.3242", "60.3930"),
    KRISTIANSAND("Kristiansand","7.9956","58.1467",),
    STAVANGER("Stavanger", "5.733107", "58.969975"),
    KRAGERØ("Kragerø", "9.4149", "58.863"),
    ARENDAL("Arendal", "8.7725", "58.4615"),
    FARE("Rørvik - Støtt", "11.987", "65.5899")

}