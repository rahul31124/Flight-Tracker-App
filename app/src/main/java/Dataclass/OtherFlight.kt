package Dataclass

data class OtherFlight(
    val airline_logo: String,
    val booking_token: String,
    val carbon_emissions: CarbonEmissions,
    val flights: List<FlightX>,
    val price: Int,
    val total_duration: Int,
    val type: String
)