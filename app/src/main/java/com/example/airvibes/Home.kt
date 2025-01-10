package com.example.airvibes


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.airvibes.databinding.FragmentHomeBinding

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
                // Check if the clicked category is "Airline"
                if (category.cname == "Airlines") {
                    // Navigate to AirlineSearch activity
                    val intent = Intent(activity, AirlineSearch::class.java)
                    intent.putExtra("selectedCategory", category.cname) // You can pass additional data if needed
                    startActivity(intent)
                }

                if (category.cname == "Tickets") {
                    // Navigate to AirlineSearch activity
                    val intent = Intent(activity, CheapAir::class.java)
                    startActivity(intent)
                }
                if (category.cname == "News        ") {
                    // Navigate to AirlineSearch activity
                    val intent = Intent(activity, AviationNews::class.java)
                    startActivity(intent)
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

        // Initialize the ViewModel and observe the categories data
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
}
