package com.example.airvibes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenSkyApi {
    @GET("states/all")
    suspend fun getFlights(
        @Query("lamin") lamin: Double,
        @Query("lamax") lamax: Double,
        @Query("lomin") lomin: Double,
        @Query("lomax") lomax: Double
    ): FlightResponse
}
interface NewsApiService {
    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>
}

