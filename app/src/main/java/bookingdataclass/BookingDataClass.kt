package bookingdataclass

data class BookingDataClass(
    val baggage_prices: BaggagePrices,
    val booking_options: List<BookingOption>,
    val price_insights: PriceInsights,
    val search_metadata: SearchMetadata,
    val search_parameters: SearchParameters,
    val selected_flights: List<SelectedFlight>
)