package com.example.wisataindonesia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.wisataindonesia.fragments.AddWisataFragment
import com.example.wisataindonesia.fragments.HomeFragment
import com.example.wisataindonesia.fragments.ProfileFragment
import com.example.wisataindonesia.model.Wisata
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    private val wisataList = mutableListOf<Wisata>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add sample data
        addSampleData()

        bottomNavigation = findViewById(R.id.bottomNavigation)
        setupBottomNavigation()

        // Load default fragment if no saved state
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }
    }

    private fun addSampleData() {
        wisataList.add(
            Wisata(
                "Bali",
                "Pulau Bali",
                "Pulau Bali adalah sebuah pulau di Indonesia yang dikenal karena memiliki pegunungan berapi yang hijau, terasering padi yang unik, pantai, dan terumbu karang yang cantik."
            )
        )
        wisataList.add(
            Wisata(
                "Borobudur",
                "Magelang, Jawa Tengah",
                "Candi Borobudur adalah candi Buddha terbesar di dunia yang dibangun pada abad ke-8."
            )
        )
    }

    private fun setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.nav_add -> {
                    loadFragment(AddWisataFragment())
                    true
                }
                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    fun getWisataList(): List<Wisata> = wisataList.toList()

    fun addWisata(wisata: Wisata) {
        wisataList.add(wisata)
    }
} 