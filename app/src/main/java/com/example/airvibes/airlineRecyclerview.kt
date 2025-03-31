package com.example.airvibes


import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView


class FlightAdapter(
    private var flightList: List<Flight>,
    private val selectedAirline: String,
    private val airlineWebsite: String?
) : RecyclerView.Adapter<FlightAdapter.FlightViewHolder>() {

    // ViewHolder to hold each flight item view
    class FlightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val airlineName: TextView = itemView.findViewById(R.id.airlinename)
        val callSign: TextView = itemView.findViewById(R.id.decelerate)
        val airlineloogo: ShapeableImageView = itemView.findViewById(R.id.airlinelooogo)
        val speed:TextView = itemView.findViewById(R.id.speed)
        val viewde:ImageView=itemView.findViewById(R.id.vieewdetails)
    }

    // Inflating the layout for each item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.airlinettools, parent, false)
        return FlightViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        val flight = flightList[position]

        // Set the airline name to the selected airline from the spinner
        holder.airlineName.text = selectedAirline

        // Optionally, you can still display the callsign if necessary
        holder.callSign.text = flight.callsign

        // Construct the full logo URL
        val webss = "https://logo.clearbit.com/" + airlineWebsite.orEmpty()
        holder.viewde.setOnClickListener {
            val intent = Intent(holder.itemView.context, FlightDetails::class.java)

            // Pass necessary flight details to FlightDetailsActivity
            intent.putExtra("CALLSIGN", flight.callsign)


            holder.itemView.context.startActivity(intent)


        }


        Log.d("FlightAdapteryyy", "Constructed Logo URL: $webss")
        val velocityKmH = flight.velocity * 3.6
        holder.speed.text = String.format("%.2f km/h", velocityKmH)

        Glide.with(holder.itemView.context)
            .load(webss)
            .apply(RequestOptions.centerCropTransform())
            .into(holder.airlineloogo)
    }



    override fun getItemCount(): Int {
        return flightList.size
    }


}
