package com.example.airvibes

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface FlightApiService {
    @GET("aedbx/aerodatabox/flights/CallSign/{callsign}?withAircraftImage=false&withLocation=false")
    suspend fun getFlightDetails(
        @Path("callsign") callsign: String,
        @Header("x-magicapi-key") apiKey: String
    ): List<FlightDetailResponse>
}

