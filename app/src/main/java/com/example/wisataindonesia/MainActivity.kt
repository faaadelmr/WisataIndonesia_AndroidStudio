package com.example.wisataindonesia

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wisataindonesia.adapter.WisataAdapter
import com.example.wisataindonesia.model.Wisata
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WisataAdapter
    private val wisataList = mutableListOf<Wisata>()

    private val addWisataLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.getParcelableExtra<Wisata>("wisata")?.let { wisata ->
                adapter.addWisata(wisata)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)

        setupRecyclerView()
        setupClickListeners(fabAdd)
    }

    private fun setupRecyclerView() {
        adapter = WisataAdapter(wisataList) { wisata ->
            showDetailFragment(wisata)
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun setupClickListeners(fabAdd: FloatingActionButton) {
        fabAdd.setOnClickListener {
            val intent = Intent(this, AddWisataActivity::class.java)
            addWisataLauncher.launch(intent)
        }
    }

    private fun showDetailFragment(wisata: Wisata) {
        val fragment = DetailWisataFragment.newInstance(wisata)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
} 