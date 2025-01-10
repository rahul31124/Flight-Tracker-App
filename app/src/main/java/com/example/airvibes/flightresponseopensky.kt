package com.example.airvibes

data class FlightResponse(
    val states: List<List<Any>>
)

data class Flight(
    val callsign: String,
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,    // Altitude in meters
    val velocity: Double,    // Velocity in meters per second
    val direction: Float     // Direction in degrees
)
