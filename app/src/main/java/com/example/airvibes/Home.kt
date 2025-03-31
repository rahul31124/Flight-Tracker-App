package com.example.airvibes


import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.airvibes.databinding.FragmentHomeBinding
import com.google.android.gms.location.LocationServices
import android.Manifest



class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryAdapter = CategoryAdapter(object : CategoryAdapter.OnItemClickListener {
            override fun onItemClick(category: Category) {
                if (category.cname == "Airlines") {
                    val intent = Intent(activity, AirlineSearch::class.java)
                    intent.putExtra("selectedCategory", category.cname)
                    startActivity(intent)
                }

                if (category.cname == "Tickets") {
                    val intent = Intent(activity, CheapAir::class.java)
                    startActivity(intent)
                }
                if (category.cname == "News        ") {
                    val intent = Intent(activity, AviationNews::class.java)
                    startActivity(intent)
                }
                if (category.cname == "Number") {
                    val intent = Intent(activity, AirlineNumber::class.java)
                    startActivity(intent)
                }
                if (category.cname == "Nearby") {
                    getCurrentLocation { latitude, longitude ->
                        val bundle = Bundle().apply {
                            putDouble("latitude", latitude)
                            putDouble("longitude", longitude)
                        }
                        val mapFragment = MapFragment()
                        mapFragment.arguments = bundle
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, mapFragment)
                            .addToBackStack(null)
                            .commit()
                    }
                }

            }
        })

        val layoutManager = GridLayoutManager(requireContext(), 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val category = categoryAdapter.currentList[position]
                return if (category.cname == "Nearby") 2 else 1
            }
        }

        binding.category.layoutManager = layoutManager
        binding.category.adapter = categoryAdapter

        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        categoryViewModel.allCategories.observe(viewLifecycleOwner, { categories ->
            categories?.let {
                categoryAdapter.submitList(it)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getCurrentLocation(callback: (Double, Double) -> Unit) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1001)
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                callback(it.latitude, it.longitude)
            } ?: run {
                Toast.makeText(requireContext(), "Unable to get location", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
}
