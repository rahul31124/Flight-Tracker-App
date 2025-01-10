package bookingdataclass

data class Flight(
    val airline: String,
    val airline_logo: String,
    val airplane: String,
    val arrival_airport: ArrivalAirport,
    val departure_airport: DepartureAirport,
    val duration: Int,
    val extensions: List<String>,
    val flight_number: String,
    val legroom: String,
    val travel_class: String
)