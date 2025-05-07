package com.example.wisataindonesia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.wisataindonesia.MainActivity
import com.example.wisataindonesia.R
import com.example.wisataindonesia.model.Wisata
import com.google.android.material.textfield.TextInputEditText

class AddWisataFragment : Fragment() {
    private lateinit var etNama: TextInputEditText
    private lateinit var etLokasi: TextInputEditText
    private lateinit var etDeskripsi: TextInputEditText
    private lateinit var btnSimpan: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_wisata, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etNama = view.findViewById(R.id.etNama)
        etLokasi = view.findViewById(R.id.etLokasi)
        etDeskripsi = view.findViewById(R.id.etDeskripsi)
        btnSimpan = view.findViewById(R.id.btnSimpan)

        btnSimpan.setOnClickListener {
            if (validateInput()) {
                val wisata = Wisata(
                    etNama.text.toString(),
                    etLokasi.text.toString(),
                    etDeskripsi.text.toString()
                )
                
                // Add wisata through MainActivity
                (activity as? MainActivity)?.addWisata(wisata)
                
                // Clear input fields
                clearInput()
                
                // Show success message
                Toast.makeText(context, "Wisata berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                
                // Navigate back to HomeFragment
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, HomeFragment())
                    .commit()
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        if (etNama.text.isNullOrEmpty()) {
            etNama.error = "Nama wisata harus diisi"
            isValid = false
        }

        if (etLokasi.text.isNullOrEmpty()) {
            etLokasi.error = "Lokasi harus diisi"
            isValid = false
        }

        if (etDeskripsi.text.isNullOrEmpty()) {
            etDeskripsi.error = "Deskripsi harus diisi"
            isValid = false
        }

        return isValid
    }

    private fun clearInput() {
        etNama.text?.clear()
        etLokasi.text?.clear()
        etDeskripsi.text?.clear()
    }
} 