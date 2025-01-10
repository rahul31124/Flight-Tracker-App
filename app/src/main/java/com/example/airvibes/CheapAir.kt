package com.example.airvibes

import Dataclass.BestFlight
import Dataclass.NGflightsDataClass
import android.R
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.airvibes.databinding.ActivityCheapAirBinding
import kotlinx.coroutines.launch
import retrofit2.Call
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CheapAir : AppCompatActivity() {
    private lateinit var binding: ActivityCheapAirBinding
  /*  private lateinit var categoryAdapter: WCategoryAdapter*/
    lateinit var d:String
    private lateinit var GflightAdapter: BestFlightsAdapter



    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCheapAirBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.cheapairlines.layoutManager = LinearLayoutManager(this)



        // Initialize the adapter

        // Populate the adapter with data (assuming you have a list of FlightSearchResponse objects)

     /*   binding.cheapairback.setOnClickListener {
            onBackPressed()
        }*/


        d=""
        binding.datepicker.setOnClickListener {
            openDatePicker { selectedDate ->
                // Do something with the selected date
                d=selectedDate
                // For example, you can now use 'selectedDate' to update the URL
                Toast.makeText(this, "Received Date: $selectedDate", Toast.LENGTH_SHORT).show()
            }
        }
        // Initialize ViewModel


        val airportMap = mapOf(
            "HHH"  to "Click To Select",
            "CCU" to "Kolkata",
            "IXA" to "Agartala",
            "BLR" to "Bangalore",
            "DEL" to "Delhi",
            "BOM" to "Mumbai",
            "HYD" to "Hyderabad",
            "MAA" to "Chennai",
            "GOI" to "Goa",
            "PNQ" to "Pune",
            "AMD" to "Ahmedabad",
            "LKO" to "Lucknow",
            "JAI" to "Jaipur",
            "PAT" to "Patna",
            "COK" to "Kochi",
            "TRV" to "Thiruvananthapuram",
            "VNS" to "Varanasi",
            "VTZ" to "Visakhapatnam",
            "BBI" to "Bhubaneswar",
            "IXC" to "Chandigarh",
            "GOP" to "Gorakhpur",
            "IMF" to "Imphal",
            "IXR" to "Ranchi",
            "SXR" to "Srinagar",
            "IXB" to "Bagdogra",
            "DIB" to "Dibrugarh",
            "GAU" to "Guwahati",
            "IXM" to "Madurai",
            "UDR" to "Udaipur",
            "BDQ" to "Vadodara",
            "TRZ" to "Tiruchirappalli",
            "JDH" to "Jodhpur"
        )








        val airportsList = airportMap.values.toList()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, airportsList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

// Set the adapter to both Spinners
        binding.fromspinner.adapter = adapter
        binding.tspinner.adapter = adapter

// Set an OnItemSelectedListener to get selected items
        binding.searchflightcheap.setOnClickListener {
            val fromCity = binding.fromspinner.selectedItem.toString() // Get selected item from fromspinner
            val toCity = binding.tspinner.selectedItem.toString()
            Log.d("SearchFlightCheap", "Selected from city: $fromCity")
            Log.d("SearchFlightCheap", "Selected to city: $toCity")

            val fromCode = airportMap.entries.find { it.value == fromCity }?.key
            val toCode = airportMap.entries.find { it.value == toCity }?.key

            Log.d("SearchFlightCheap", "Mapped from city code: $fromCode")
            Log.d("SearchFlightCheap", "Mapped to city code: $toCode")

            if (fromCode != null && toCode != null) {
                if (fromCode.isNotEmpty() && toCode.isNotEmpty()) {
                    Log.d("SearchFlightCheap", "From and To codes are valid. Proceeding with the search.")

                    // Clear the existing items in the RecyclerView
                   /* categoryAdapter.clearItems()
                    categoryAdapter.notifyDataSetChanged()*/
                    Log.d("SearchFlightCheap", "RecyclerView cleared.")
                    // Fetch data

                    lifecycleScope.launch {
                        try {
                            val response = RetrofitInstance3.api.getFlightSearchResults(
                                departureId = fromCode,
                                arrivalId = toCode,
                                outboundDate = d,
                                adults = 1
                            )

                            if (response.isSuccessful) {
                                val flightResponse: NGflightsDataClass? = response.body()
                                val bestFlights: List<BestFlight>? = flightResponse?.best_flights

                                if (!bestFlights.isNullOrEmpty()) {
                                    // Filter flights where the price is greater than 0
                                    val filteredFlights = bestFlights.filter { it.price > 0 }

                                    if (filteredFlights.isNotEmpty()) {
                                        GflightAdapter = BestFlightsAdapter(filteredFlights, fromCode, toCode, d)
                                        binding.cheapairlines.adapter = GflightAdapter
                                    } else {
                                        Log.d("APIYY", "No flights with price > 0")
                                    }
                                } else {
                                    Log.d("APIYY", "No best flights available")
                                }
                            } else {
                                Log.e("APIYY", "Error: ${response.errorBody()?.string()}")
                            }
                        } catch (e: Exception) {
                            Log.e("APIYY", "Exception: ${e.message}")
                        }
                    }












                } else {
                    Log.w("SearchFlightCheap", "From or To codes are empty.")
                    Toast.makeText(this, "Please select valid cities.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.w("SearchFlightCheap", "From or To city mapping failed.")
                Toast.makeText(this, "Please select valid cities.", Toast.LENGTH_SHORT).show()
            }
        }










    }

    // Handle item clicks



    private fun openDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()

        // Initialize DatePickerDialog with current date
        val datePickerDialog = DatePickerDialog(
            this,
            { view, year, month, dayOfMonth ->
                // Create a Calendar object for the selected date
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                // Format the selected date
                val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate = format.format(selectedDate.time) // String representation of the date

                // Use the callback to return the formatted date
                onDateSelected(formattedDate)

                // Display the selected date (can be used for any purpose like logging or displaying in a Toast)
                Toast.makeText(this, "Selected Date: $formattedDate", Toast.LENGTH_SHORT).show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Show the DatePickerDialog
        datePickerDialog.show()
    }





















}
