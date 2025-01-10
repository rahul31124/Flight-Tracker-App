package bookingdataclass

data class SearchParameters(
    val arrival_id: String,
    val booking_token: String,
    val currency: String,
    val departure_id: String,
    val engine: String,
    val gl: String,
    val hl: String,
    val outbound_date: String,
    val type: String
)