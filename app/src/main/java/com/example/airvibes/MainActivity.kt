package com.example.airvibes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.airvibes.databinding.ActivityMainBinding
import nl.joery.animatedbottombar.AnimatedBottomBar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the bottom navigation
        val bottomNavigationView = binding.bottomNavigation // Use binding to access the bottom navigation

        // Set an OnTabSelectListener for custom behavior when a tab is selected
        bottomNavigationView.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                // Handle fragment change on tab selection
                when (newIndex) {
                    0 -> {
                        // Show HomeFragment
                        replaceFragment(HomeFragment())
                    }
                    1 -> {
                        // Show MapFragment
                        replaceFragment(MapFragment())
                    }
                    // Add more tabs and fragments here if needed
                    else -> {
                        // Handle other tabs
                    }
                }
            }

            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {
                // Optional: Handle reselection of the same tab if needed
            }
        })

        // Set the initial fragment if it's the first time loading
        if (savedInstanceState == null) {
            bottomNavigationView.selectTabAt(0)  // Automatically select the first tab
            replaceFragment(HomeFragment())  // Load the HomeFragment initially
        }
    }

    // Helper method to replace fragments
    private fun replaceFragment(fragment: androidx.fragment.app.Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)  // Replace the container with the new fragment
        transaction.commit()
    }
}
