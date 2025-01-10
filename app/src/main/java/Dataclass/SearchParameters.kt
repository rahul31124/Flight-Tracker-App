package Dataclass

data class SearchParameters(
    val adults: Int,
    val arrival_id: String,
    val departure_id: String,
    val engine: String,
    val gl: String,
    val hl: String,
    val outbound_date: String,
    val type: String
)