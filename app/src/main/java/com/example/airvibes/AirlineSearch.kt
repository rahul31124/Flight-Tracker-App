package com.example.airvibes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.airvibes.databinding.ActivityAirlineSearchBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AirlineSearch : AppCompatActivity() {

    private lateinit var airlineMap: Map<String, String>
    private lateinit var spinner: Spinner
    private lateinit var airlineWebsiteMap: Map<String, String>
    private lateinit var binding:ActivityAirlineSearchBinding
    private lateinit var flightsAdapter: FlightAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAirlineSearchBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.backbtnlist.setOnClickListener {
            onBackPressed()
        }


        spinner = findViewById(R.id.spinner)




        airlineMap = mapOf(
            // International Airlines
            "AIC" to "Air India",         // India
            "AAL" to "American Airlines", // USA
            "BAW" to "British Airways",   // UK
            "DLH" to "Lufthansa",
            "AIQ" to "AirAsia",
            "UAE" to "Emirates",          // UAE
            "AFR" to "Air France",        // France
            "KLM" to "KLM",               // Netherlands
            "QTR" to "Qatar Airways",     // Qatar
            "JAL" to "Japan Airlines",    // Japan
            "NZ" to "Air New Zealand",   // New Zealand
            "CPA" to "Cathay Pacific",    // Hong Kong
            "SIA" to "Singapore Airlines",// Singapore
            "THA" to "Thai Airways",      // Thailand
            "SU" to "Aeroflot",          // Russia
            "ACA" to "Air Canada",        // Canada
            "QFA" to "Qantas",            // Australia
            "AZ" to "Alitalia",          // Italy
            "VIR" to "Virgin Atlantic",   // UK // Germany
            "THY" to "Turkish Airlines",  // Turkey
            "SK" to "SAS",               // Sweden
            "SA" to "South African Airways", // South Africa
            "IB" to "Iberia",            // Spain
            "MU" to "China Eastern",     // China
            "CCA" to "Air China",         // China

            // Indian Airlines
            "AIC" to "Air India",         // Air India (Indian)
            "SEJ" to "SpiceJet",          // SpiceJet (Indian)
            "GOW" to "GoAir",             // GoAir (Indian)
            "IGO" to "IndiGo",            // IndiGo (Indian)
            "UK" to "Vistara",           // Vistara (Indian)
            "IAD" to "AirAsia India",     // AirAsia India (Indian)
            "AKJ" to "Akasa Air",         // Akasa Air (Indian)
            "BL" to "Alliance Air",      // Alliance Air (Indian)
            "AXB" to "Air India Express", // Air India Express (Indian)
        )

        airlineWebsiteMap = mapOf(
            "Air India" to "https://www.airindia.in",
            "American Airlines" to "https://www.aa.com",
            "British Airways" to "https://www.britishairways.com",
            "Lufthansa" to "https://www.lufthansa.com",
            "Emirates" to "https://www.emirates.com",
            "Air France" to "https://www.airfrance.com",
            "KLM" to "https://www.klm.com",
            "Qatar Airways" to "https://www.qatarairways.com",
            "Japan Airlines" to "https://www.jal.co.jp/en",
            "Air New Zealand" to "https://www.airnewzealand.com",
            "Cathay Pacific" to "https://www.cathaypacific.com",
            "Singapore Airlines" to "https://www.singaporeair.com",
            "Thai Airways" to "https://www.thaiairways.com",
            "Aeroflot" to "https://www.aeroflot.ru",
            "Air Canada" to "https://www.aircanada.com",
            "Qantas" to "https://www.qantas.com",
            "Alitalia" to "https://www.alitalia.com",
            "Virgin Atlantic" to "https://www.virginatlantic.com",
            "Turkish Airlines" to "https://www.turkishairlines.com",
            "SAS" to "https://www.flysas.com",
            "South African Airways" to "https://www.flysaa.com",
            "Iberia" to "https://www.iberia.com",
            "China Eastern" to "https://www.ceair.com",
            "Air China" to "https://www.airchina.com",
            "SpiceJet" to "https://www.spicejet.com",
            "GoAir" to "https://www.goair.in",
            "IndiGo" to "https://www.goindigo.in",
            "Vistara" to "https://www.airvistara.com",
            "AirAsia India" to "https://www.airasia.com",
            "Akasa Air" to "https://www.akasaair.com",
            "Alliance Air" to "https://www.allianceair.in",
            "TATA Group" to "https://www.tata.com",
            "Indian Airlines" to "https://www.indianairlines.in",
            "Air India Express" to "https://www.airindiaexpress.in"
        )

        val airlinesList = airlineMap.values.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, airlinesList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedAirline = parent.getItemAtPosition(position) as String
                fetchAndDisplayFlights(selectedAirline)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun fetchAndDisplayFlights(selectedAirline: String) {
        Log.d(
            "AirlineSearchyy",
            "Fetching flights for airline: $selectedAirline"
        )
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    Log.d("AirlineSearchyy", "Making API call to fetch flights")
                    RetrofitInstance.api.getFlights(
                        lamin = 0.0, lamax = 60.0, lomin = 60.0, lomax = 180.0
                    )
                }

                if (response.states.isNullOrEmpty()) {
                    Log.w("AirlineSearchyy", "No flight data available.")
                    return@launch
                } else {
                    Log.d("AirlineSearchyy", "API response contains ${response.states.size} flights")
                }


                val filteredFlights = filterFlightsByAirline(response.states, selectedAirline,)
                Log.d("AirlineSearchyy", "Filtered flights count: ${filteredFlights.size}")


                val selectedAirlineCallsign = airlineMap.entries.find { it.value == selectedAirline }?.key
                val airlineWebsite = airlineWebsiteMap [selectedAirline]
                Log.d("AirlineSearchyy", "Selected airline callsign: $selectedAirlineCallsign")
                Log.d("AirlineSearchwebsite", "Retrieved airline website: $airlineWebsite")
                flightsAdapter = FlightAdapter(filteredFlights, selectedAirline, airlineWebsite)
                findViewById<RecyclerView>(R.id.airlinerecyler).apply {
                    layoutManager = LinearLayoutManager(this@AirlineSearch)
                    adapter = flightsAdapter
                }

            } catch (e: Exception) {
                Log.e("AirlineSearchyy", "Error fetching flights", e)
            }
        }
    }


    private fun filterFlightsByAirline(flights: List<List<Any>>, selectedAirline: String): List<Flight> {
        Log.d("AirlineSearchyy", "Filtering flights for selected airline: $selectedAirline")

        val selectedAirlineCallsignPrefix = airlineMap.entries.find { it.value == selectedAirline }?.key
        Log.d("AirlineSearchyy", "Selected airline callsign prefix: $selectedAirlineCallsignPrefix")


        return if (selectedAirlineCallsignPrefix != null) {
            flights.mapNotNull { flight ->



                val flightCallsignPrefix = (flight[1] as String).takeWhile { it.isLetter() }.take(3)


                Log.d(
                    "AirlineSearchyy",
                    "Selected Airline: $selectedAirline; Callsign Prefix from Map: $selectedAirlineCallsignPrefix; " +
                            "Extracted Callsign Prefix from Flight: $flightCallsignPrefix"
                )



                if (flightCallsignPrefix == selectedAirlineCallsignPrefix) {
                    try {
                        val latitude = (flight[6] as? Number)?.toDouble() ?: 0.0
                        val longitude = (flight[5] as? Number)?.toDouble() ?: 0.0
                        val altitude = (flight[7] as? Number)?.toDouble() ?: 0.0
                        val velocity = (flight[9] as? Number)?.toDouble() ?: 0.0
                        val direction = (flight[10] as? Number)?.toFloat() ?: 0f

                        Log.d(
                            "AirlineSearchyy",
                            "Parsed flight details - Latitude: $latitude, Longitude: $longitude, Altitude: $altitude, Velocity: $velocity, Direction: $direction"
                        )


                        Flight(
                            callsign = flight[1] as String,
                            latitude = latitude,
                            longitude = longitude,
                            altitude = altitude,
                            velocity = velocity,
                            direction = direction
                        )
                    } catch (e: Exception) {
                        Log.e("AirlineSearchyy", "Error parsing flight data", e)
                        null
                    }
                }
                else {
                    Log.d("AirlineSearchyy", "Flight ${flight[1]} does not match selected airline prefix")
                    null
                }
            }
        } else {
            Log.w("AirlineSearchyy", "No matching callsign prefix found for selected airline")
            emptyList()
        }
    }

}