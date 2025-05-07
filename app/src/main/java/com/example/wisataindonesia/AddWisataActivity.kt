package com.example.wisataindonesia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.wisataindonesia.model.Wisata

class AddWisataActivity : AppCompatActivity() {
    private lateinit var etNama: EditText
    private lateinit var etLokasi: EditText
    private lateinit var etDeskripsi: EditText
    private lateinit var btnSimpan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_wisata)

        etNama = findViewById(R.id.etNama)
        etLokasi = findViewById(R.id.etLokasi)
        etDeskripsi = findViewById(R.id.etDeskripsi)
        btnSimpan = findViewById(R.id.btnSimpan)

        btnSimpan.setOnClickListener {
            val nama = etNama.text.toString()
            val lokasi = etLokasi.text.toString()
            val deskripsi = etDeskripsi.text.toString()

            if (nama.isNotEmpty() && lokasi.isNotEmpty() && deskripsi.isNotEmpty()) {
                val wisata = Wisata(nama, lokasi, deskripsi)
                val intent = Intent().apply {
                    putExtra("wisata", wisata)
                }
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
} 