package bookingdataclass

data class Together(
    val airline_logos: List<String>,
    val baggage_prices: List<String>,
    val book_with: String,
    val booking_request: BookingRequest,
    val marketed_as: List<String>,
    val price: Int
)