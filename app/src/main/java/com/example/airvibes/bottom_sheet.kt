package com.example.airvibes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.imageview.ShapeableImageView


class FlightDetailsBottomSheet : BottomSheetDialogFragment() {

    companion object {
        private const val ARG_CALLSIGN = "callsign"
        private const val ARG_VELOCITY = "velocity"
        private const val ARG_ALTITUDE="altitude"

        fun newInstance(callsign: String, velocity: Double,altitude:Double): FlightDetailsBottomSheet {
            val fragment = FlightDetailsBottomSheet()
            val bundle = Bundle()
            bundle.putString(ARG_CALLSIGN, callsign)
            bundle.putDouble(ARG_VELOCITY, velocity)
            bundle.putDouble(ARG_ALTITUDE, altitude)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet, container, false)

        val callsign = arguments?.getString(ARG_CALLSIGN)
        val velocity = arguments?.getDouble(ARG_VELOCITY)
        val altitude = arguments?.getDouble(ARG_ALTITUDE)
        val prefix = callsign?.take(3)?.uppercase()

        val airlineLogoImageView = view?.findViewById<ShapeableImageView>(R.id.airlinelooogo)
        val airlineNameTextView = view?.findViewById<TextView>(R.id.airlinename)
        val decelerateTextView = view?.findViewById<TextView>(R.id.decelerate)
        val speedTextView = view?.findViewById<TextView>(R.id.speed)
        val alt = view?.findViewById<TextView>(R.id.altitude)


        // Initialize the map of callsigns and airline names
       var airlineMap = mapOf(
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
            "AXB" to "Air India Express", // Air India Express (Indian) // TATA Group
            // Indian Airlines
        )
       val airline= airlineMap[prefix]
       var airlineWebsiteMap = mapOf(
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


val website= airlineWebsiteMap[airline]


        airlineNameTextView?.text = airline
        decelerateTextView?.text = "CallSign: $callsign"

        speedTextView?.text = "Speed: ${"%.2f".format(velocity ?: 0.0)} m/s"




        alt?.text = "Altitude: ${"%.2f".format(altitude ?: 0.0)} m"



        val url = "https://logo.clearbit.com/$website"
        if (airlineLogoImageView != null) {
            Glide.with(requireContext())
                .load(url)
                .into(airlineLogoImageView)
        }



        val viewDetailsButton = view?.findViewById<ImageView>(R.id.vieewdetails)

        viewDetailsButton?.setOnClickListener {
            val callSign = arguments?.getString(ARG_CALLSIGN)


            val intent = Intent(requireContext(), FlightDetails::class.java).apply {
                putExtra("CALLSIGN", callSign)
            }


            startActivity(intent)
        }


        return view
    }
}
