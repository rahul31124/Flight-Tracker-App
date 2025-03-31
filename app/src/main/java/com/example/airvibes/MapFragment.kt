package com.example.airvibes

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val latitude = arguments?.getDouble("latitude") ?: 0.0
        val longitude = arguments?.getDouble("longitude") ?: 0.0
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this) ?: run {
            Log.e("MapFragment", "Map Fragment is null")
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val latitude = arguments?.getDouble("latitude")
        val longitude = arguments?.getDouble("longitude")

        val initialLocation = if (latitude != null && longitude != null) {
            LatLng(latitude, longitude)
        } else {
            LatLng(30.0, 90.0)
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 3f))


        startFetchingFlightDataPeriodically()







    }

    private fun startFetchingFlightDataPeriodically() {
        lifecycleScope.launch {
            while (true) {
                fetchAndDisplayFlights()
                delay(30000)
            }
        }
    }



    private fun fetchAndDisplayFlights() {
        lifecycleScope.launch {
            try {
                val latitude = arguments?.getDouble("latitude")
                val longitude = arguments?.getDouble("longitude")
                val lamin: Double
                val lamax: Double
                val lomin: Double
                val lomax: Double

                if (latitude != null && longitude != null) {
                    lamin = latitude - 5.0
                    lamax = latitude + 5.0
                    lomin = longitude - 5.0
                    lomax = longitude + 5.0
                } else {
                    lamin = 0.0
                    lamax = 60.0
                    lomin = 60.0
                    lomax = 180.0
                }
                val response = withContext(Dispatchers.IO) {
                    RetrofitInstance.api.getFlights(
                        lamin = lamin, lamax = lamax, lomin = lomin, lomax = lomax
                    )
                }

                if (response.states.isNullOrEmpty()) {
                    Log.w("MapFragment", "No flight data available.")
                    return@launch
                }

                val markersToAdd = withContext(Dispatchers.Default) {
                    response.states.mapNotNull { state ->
                        try {
                            val latitude = (state[6] as? Double) ?: return@mapNotNull null
                            val longitude = (state[5] as? Double) ?: return@mapNotNull null

                            val callsign = (state[1] as? String) ?: "Unknown"
                            val direction = (state[10] as? Double) ?: 0.0
                            val velocity = (state[9] as? Double) ?: 0.0
                            val alt = (state[7] as? Double) ?: 0.0

                            val position = LatLng(latitude, longitude)


                            val resizedBitmap = BitmapFactory.decodeResource(resources, R.drawable.newmarkerairpplane)?.let {
                                Bitmap.createScaledBitmap(it, (it.width * 0.05).toInt(), (it.height * 0.05).toInt(), false)
                            } ?: return@mapNotNull null

                            val markerIcon = BitmapDescriptorFactory.fromBitmap(resizedBitmap)

                            val markerOptions = MarkerOptions()
                                .position(position)
                                .title("Flight: $callsign")
                                .icon(markerIcon)
                                .rotation(direction.toFloat())
                                .flat(true)


                            Pair(markerOptions, Triple(callsign, velocity,alt))

                        } catch (e: Exception) {
                            Log.w("MapFragment", "Error processing state: $state", e)
                            null
                        }
                    }
                }

                withContext(Dispatchers.Main) {
                    mMap.clear()
                    markersToAdd.forEach { pair ->
                        val marker = mMap.addMarker(pair.first)
                        marker?.tag = pair.second
                    }
                }


                mMap.setOnMarkerClickListener { marker ->
                    val (callsign, velocity, alt) = marker.tag as? Triple<String, Double, Double> ?: return@setOnMarkerClickListener false
                    showBottomSheet(callsign, velocity, alt)
                    true
                }

            } catch (e: Exception) {
                Log.e("MapFragment", "Error fetching flight data: ${e.message}", e)
            }
        }
    }

    private fun showBottomSheet(callsign: String, velocity: Double, alt: Double) {
        val bottomSheetFragment = FlightDetailsBottomSheet.newInstance(callsign, velocity, alt)
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }









}

