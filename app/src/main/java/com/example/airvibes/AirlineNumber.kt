package com.example.airvibes

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.airvibes.databinding.ActivityAirlineNumberBinding
import com.example.airvibes.databinding.ActivityAirlineSearchBinding

class AirlineNumber : AppCompatActivity() {
    private lateinit var binding: ActivityAirlineNumberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityAirlineNumberBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val imageUrl = "https://img.freepik.com/premium-vector/man-is-looking-phone-with-picture-plane-it_1187092-1457.jpg?w=1380"
        Glide.with(this)
            .load(imageUrl)
            .into(binding.flightsearchimgg)


        val imageUrl2 = "https://img.freepik.com/premium-vector/art-illustration_684734-58.jpg?w=826"
        Glide.with(this)
            .load(imageUrl2)
            .into(binding.sceneplane)

        setContentView(binding.root)
        binding.Searchflno.setOnClickListener{
            val flightNumber = binding.flightsearch.text.toString().trim()
            if ( flightNumber.isNotEmpty()){
                val intent = Intent(this,FlightDetails::class.java)
                intent.putExtra("CALLSIGN", flightNumber)
                startActivity(intent)
            }
        }

    }
}