package com.example.airvibes

data class FlightDetailResponse(
    val greatCircleDistance: Distance,
    val departure: FlightEvent,
    val arrival: FlightEvent,
    val number: String,
    val callSign: String,
    val status: String,
    val codeshareStatus: String,
    val isCargo: Boolean,
    val aircraft: Aircraft,
    val airline: Airline,
    val lastUpdatedUtc: String
)

data class Distance(
    val meter: Double,
    val km: Double,
    val mile: Double,
    val nm: Double,
    val feet: Double
)

data class FlightEvent(
    val airport: Airport,
    val scheduledTime: TimeInfo,
    val revisedTime: TimeInfo?,
    val terminal: String?,
    val quality: List<String>
)

data class Airport(
    val icao: String,
    val iata: String,
    val name: String,
    val shortName: String,
    val municipalityName: String,
    val location: Location,
    val countryCode: String,
    val timeZone: String
)

data class Location(
    val lat: Double,
    val lon: Double
)

data class TimeInfo(
    val utc: String,
    val local: String
)

data class Aircraft(
    val reg: String,
    val modeS: String,
    val model: String
)

data class Airline(
    val name: String,
    val iata: String,
    val icao: String
)
