data class FlightResponse(
    val flights: List<FlightG>
)

data class FlightG(
    val departure_airport: String,
    val departure_time: String,
    val arrival_airport: String,
    val arrival_time: String,
    val duration: Int,
    val airline: String,
    val airplane: String,
    val flight_number: String,
    val price: Double,
    val airline_logo_url: String
)
