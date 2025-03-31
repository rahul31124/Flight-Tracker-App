package com.example.airvibes

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.airvibes.FlightDetailResponse
import com.example.airvibes.RetrofitInstance2
import com.example.airvibes.databinding.ActivityFlightDetailsBinding
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

import com.bumptech.glide.request.target.Target

import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


class FlightDetails : AppCompatActivity() {

    private lateinit var binding: ActivityFlightDetailsBinding
    private val apiKey = "cm2eqfwim0001l603kig05ey7"
    private val TAG = "FlightDetailyy"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlightDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val callSign = intent.getStringExtra("CALLSIGN")
        Log.d(TAG, "Received CallSign: $callSign")
        binding.backbtndetails.setOnClickListener {
            onBackPressed()
            finish()
        }
        if (callSign != null) {
            fetchFlightDetails(callSign)
        } else {
            Log.e(TAG, "CallSign not provided!")
            Toast.makeText(this, "CallSign not provided!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchFlightDetails(callSign: String) {
        Log.d(TAG, "Fetching flight details for CallSign: $callSign")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val requestUrl = "https://api.magicapi.dev/api/v1/aedbx/aerodatabox/flights/CallSign/$callSign?withAircraftImage=false&withLocation=false"
                Log.d(TAG, "Requesting URL: $requestUrl")

                val cleanCallsign = callSign.trim()
                val response = RetrofitInstance2.api2.getFlightDetails(
                    callsign = cleanCallsign,
                    apiKey = apiKey
                )

                Log.d(TAG, "API response received: $response")

                withContext(Dispatchers.Main) {
                    if (response.isNotEmpty()) {
                        Log.d(TAG, "Flight details found: ${response.first()}")
                        updateUI(response.first())
                    } else {
                        Log.w(TAG, "No flight details found for CallSign: $callSign")
                        Toast.makeText(this@FlightDetails, "No details found!", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                withContext(Dispatchers.Main) {
                    Log.e(TAG, "HTTP error while fetching details for CallSign: $callSign - $errorBody", e)
                    Toast.makeText(this@FlightDetails, "Error: $errorBody", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e(TAG, "Unexpected error while fetching details for CallSign: $callSign", e)
                    Toast.makeText(this@FlightDetails, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private val imageCache = mutableMapOf<String, String?>()

    private fun updateUI(flightDetail: FlightDetailResponse) {
        Log.d("loggy", "Starting to update UI with flight details.")

        val reg = flightDetail.aircraft.reg?.toString() ?: run {
            Log.d("loggy", "Aircraft registration is null.")
            return
        }
        binding.aircraftmodel.text=flightDetail.aircraft.model

        binding.arrivalterminal.text=flightDetail.arrival.terminal
        binding.depairport.text= flightDetail.departure.airport.name
        binding.depstatus.text=flightDetail.status
        binding.deptime.text=flightDetail.departure.scheduledTime.utc
        binding.arrairport.text=flightDetail.arrival.airport.name
        binding.arrstatus.text=flightDetail.status
        binding.arrtime.text=flightDetail.arrival.scheduledTime.utc





        binding.loadingAnimation.apply {
            visibility = View.VISIBLE
            playAnimation()
        }

        if (imageCache.containsKey(reg)) {
            val cachedUrl = imageCache[reg]
            Log.d("loggy", "Image URL loaded from cache: $cachedUrl")
            Glide.with(this)
                .load(cachedUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.loadingAnimation.apply {
                            visibility = View.GONE
                            cancelAnimation()
                        }
                        Log.d("loggy", "Failed to load cached image.")
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.loadingAnimation.apply {
                            visibility = View.GONE
                            cancelAnimation()
                        }
                        Log.d("loggy", "Cached image loaded successfully.")
                        return false
                    }
                })
                .into(binding.imgaircraft)
        } else {
            lifecycleScope.launch {
                val imageUrl = fetchImageUrlAsync(reg)
                if (!isDestroyed && imageUrl != null) {
                    imageCache[reg] = imageUrl
                    Glide.with(this@FlightDetails)
                        .load(imageUrl)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                binding.loadingAnimation.apply {
                                    visibility = View.GONE
                                    cancelAnimation()
                                }
                                Log.d("loggy", "Failed to fetch and load image URL.")
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                binding.loadingAnimation.apply {
                                    visibility = View.GONE
                                    cancelAnimation()
                                }
                                Log.d("loggy", "Image loaded and cached successfully: $imageUrl")
                                return false
                            }
                        })
                        .into(binding.imgaircraft)
                } else {
                    binding.loadingAnimation.apply {
                        visibility = View.GONE
                        cancelAnimation()
                    }
                    Log.d("loggy", "Failed to fetch image URL.")
                }
            }
        }


        binding.depart.text = "(${flightDetail.departure.airport.iata})"
        binding.arrival.text = "(${flightDetail.arrival.airport.iata})"
        Log.d("loggy", "Departure and arrival text set on UI.")
    }



    private suspend fun fetchImageUrlAsync(reg: String): String? = withContext(Dispatchers.Main) {
        val webView = WebView(this@FlightDetails)
        val uri = "https://www.planespotters.net/photos/reg/$reg"
        Log.d("loggy", "Loading URL in WebView: $uri")

        val deferredImageUrl = CompletableDeferred<String?>()

        webView.apply {
            settings.javaScriptEnabled = true
            visibility = View.INVISIBLE
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    Log.d("loggy", "Page loaded in WebView: $url")

                    evaluateJavascript(
                        "document.querySelector('img.photo_card__photo').src",
                      { value ->
                            deferredImageUrl.complete(
                                if (value.isNotEmpty()) value.replace("\"", "") else null
                            )
                        }
                    )
                }
            }
        }
        webView.loadUrl(uri)
        val result = deferredImageUrl.await()

        Log.d("loggy", "Fetched Image URL: $result")
        return@withContext result
    }












}








