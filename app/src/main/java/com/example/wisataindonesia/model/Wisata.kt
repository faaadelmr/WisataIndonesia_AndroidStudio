package com.example.wisataindonesia.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Wisata(
    val nama: String,
    val lokasi: String,
    val deskripsi: String,
    val imageUrl: String = "https://example.com/default-image.jpg" // Default image URL
) : Parcelable 