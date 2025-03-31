package com.example.airvibes

import Dataclass.BestFlight
import Dataclass.Flight
import Dataclass.NGflightsDataClass
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import bookingdataclass.BookingDataClass
import com.bumptech.glide.Glide
import com.example.airvibes.R
import kotlinx.coroutines.GlobalScope

import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


class BestFlightsAdapter(
    private val bestFlights: List<BestFlight>,
    private val fromCode: String,
    private val toCode: String,
    private val outboundDate: String
) : RecyclerView.Adapter<BestFlightsAdapter.BestFlightViewHolder>() {

    inner class BestFlightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvAirline: TextView = itemView.findViewById(R.id.websitename)
        private val tvPrice: TextView = itemView.findViewById(R.id.priceyy)
        private val tvImg: ImageView = itemView.findViewById(R.id.airlinelooogocheap)
        private val tvBook: TextView = itemView.findViewById(R.id.book)

        fun bind(bestFlight: BestFlight) {
            val firstFlight = bestFlight.flights.firstOrNull()
            if (firstFlight != null) {
                tvAirline.text = firstFlight.airline


                Glide.with(itemView.context)
                    .load(firstFlight.airline_logo)
                    .into(tvImg)
                val departureTime = convertTo12HourFormat(firstFlight.departure_airport.time)
                val arrivalTime = convertTo12HourFormat(firstFlight.arrival_airport.time)

                val tvDepartureTime: TextView = itemView.findViewById(R.id.deptime)
                val tvArrivalTime: TextView = itemView.findViewById(R.id.arrtime)

                tvDepartureTime.text = "$departureTime"
                tvArrivalTime.text = "$arrivalTime"





            } else {
                tvAirline.text = "Unknown Airline"

            }

            tvPrice.text = "₹${bestFlight.price}"


            tvBook.setOnClickListener {
                callAnotherAPI(bestFlight, it.context)
            }
        }

        private fun callAnotherAPI(bestFlight: BestFlight, context: Context) {
            GlobalScope.launch {
                try {
                    val response = RetrofitInstance4.api3.getFlightSearchResults(
                        departureId = fromCode,
                        arrivalId = toCode,
                        outboundDate = outboundDate,
                        adults = 1,
                        bookingToken = bestFlight.booking_token
                    )

                    if (response.isSuccessful) {
                        val flightResponse: BookingDataClass? = response.body()


                        flightResponse?.let {

                            val postData =
                                it.booking_options.firstOrNull()?.together?.booking_request?.post_data
                            val url = "https://www.google.com/travel/clk/f?$postData"
                            openLinkInChromeTab(context, url)


                            postData?.let {
                                Log.d("APIYY", "Post Data: $postData")
                            } ?: Log.e("APIYY", "Post data is null")
                        }
                    }else {
                        Log.e("APIYY", "Error: ${response.errorBody()?.string()}")
                    }







                } catch (e: Exception) {
                    Log.e("APIYY", "Exception: ${e.message}")
                }
            }


        }

        private fun openLinkInChromeTab(context: Context, url: String) {

            val uri = Uri.parse(url)


            val builder = CustomTabsIntent.Builder()


            builder.setToolbarColor(ContextCompat.getColor(context, R.color.white))


            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(context, uri)
        }
        fun convertTo12HourFormat(timeString: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")

            val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            outputFormat.timeZone = TimeZone.getTimeZone("Asia/Kolkata")

            return try {
                val date = inputFormat.parse(timeString)
                outputFormat.format(date)
            } catch (e: Exception) {
                "Invalid Time"
            }
        }










    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestFlightViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cheapairlinetools, parent, false)
        return BestFlightViewHolder(view)
    }

    override fun onBindViewHolder(holder: BestFlightViewHolder, position: Int) {
        holder.bind(bestFlights[position])
    }

    override fun getItemCount(): Int = bestFlights.size
}
