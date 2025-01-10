package bookingdataclass

data class SelectedFlight(
    val airline_logo: String,
    val carbon_emissions: CarbonEmissions,
    val flights: List<Flight>,
    val total_duration: Int,
    val type: String
)