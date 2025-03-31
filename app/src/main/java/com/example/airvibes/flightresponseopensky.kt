package com.example.airvibes

data class FlightResponse(
    val states: List<List<Any>>
)

data class Flight(
    val callsign: String,
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    val velocity: Double,
    val direction: Float
)
