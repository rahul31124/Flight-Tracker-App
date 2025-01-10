package com.example.airvibes


import Dataclass.NGflightsDataClass
import FlightResponse
import bookingdataclass.BookingDataClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlightsApiService {
    @GET("search")
    suspend fun getFlightSearchResults(
        @Query("engine") engine: String = "google_flights",
        @Query("departure_id") departureId: String,
        @Query("arrival_id") arrivalId: String,
        @Query("outbound_date") outboundDate: String,
        @Query("type") type: Int = 2,
        @Query("adults") adults: Int = 1,
        @Query("gl") gl: String = "us",
        @Query("hl") hl: String = "en",
        @Query("currency") currency: String = "INR",
        @Query("api_key") apiKey: String ="de3de255adb2fbc09a396d61438a9576c23c97f7ac177e776843d51e4176f112"
    ): Response<NGflightsDataClass>
}

interface FlightsApiService2 {
    @GET("search")
    suspend fun getFlightSearchResults(
        @Query("engine") engine: String = "google_flights",
        @Query("departure_id") departureId: String,
        @Query("arrival_id") arrivalId: String,
        @Query("outbound_date") outboundDate: String,
        @Query("type") type: Int = 2,
        @Query("adults") adults: Int = 1,
        @Query("gl") gl: String = "us",
        @Query("hl") hl: String = "en",
        @Query("currency") currency: String = "INR",
        @Query("booking_token") bookingToken: String,
        @Query("api_key") apiKey: String ="de3de255adb2fbc09a396d61438a9576c23c97f7ac177e776843d51e4176f112"
    ): Response<BookingDataClass>
}

