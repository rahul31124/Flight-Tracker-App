package com.example.airvibes

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://opensky-network.org/api/"

    val api: OpenSkyApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenSkyApi::class.java)
    }
}


object RetrofitInstance2 {
    private const val BASE_URL2 = "https://api.magicapi.dev/api/v1/"

    val api2: FlightApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FlightApiService::class.java)
    }
}
object RetrofitInstance3 {
    private const val BASE_URL = "https://serpapi.com/"

    // Add logging interceptor
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val api: FlightsApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)  // Use the OkHttpClient with logging
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FlightsApiService::class.java)
    }
}
object RetrofitInstance4 {
    private const val BASE_URL2 = "https://serpapi.com/"

    val api3: FlightsApiService2 by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FlightsApiService2::class.java)
    }
}

