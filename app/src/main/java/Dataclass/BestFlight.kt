package Dataclass

data class BestFlight(
    val airline_logo: String,
    val booking_token: String,
    val carbon_emissions: CarbonEmissions,
    val flights: List<Flight>,
    val price: Int,
    val total_duration: Int,
    val type: String
)