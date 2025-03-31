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


        val bottomNavigationView = binding.bottomNavigation


        bottomNavigationView.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {


                when (newIndex) {
                    0 -> {
                        replaceFragment(HomeFragment())
                    }
                    1 -> {

                        replaceFragment(MapFragment())
                    }
                    else -> {
                    }
                }
            }

            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {

            }
        })


        if (savedInstanceState == null) {
            bottomNavigationView.selectTabAt(0)
            replaceFragment(HomeFragment())
        }
    }


    private fun replaceFragment(fragment: androidx.fragment.app.Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}
