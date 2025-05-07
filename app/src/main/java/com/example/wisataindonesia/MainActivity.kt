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
                "Candi Prambanan",
                "Daerah istimewa Yogyakarta",
                "Candi Prambanan (bahasa Jawa: ꦕꦟ꧀ꦝꦶꦥꦿꦩ꧀ꦧꦤꦤ꧀, translit. Caṇḍi Prambanan) adalah bangunan candi bercorak agama Hindu terbesar di Indonesia yang dibangun pada abad ke-9 Masehi. Candi yang juga disebut sebagai Rara Jonggrang ini dipersembahkan untuk Trimurti, tiga dewa utama Hindu yaitu dewa Brahma sebagai dewa pencipta, dewa Wisnu sebagai dewa pemelihara, dan dewa Siwa sebagai dewa pemusnah."
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