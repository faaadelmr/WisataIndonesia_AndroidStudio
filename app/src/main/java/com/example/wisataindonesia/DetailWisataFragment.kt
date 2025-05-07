package com.example.wisataindonesia

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.wisataindonesia.model.Wisata
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class DetailWisataFragment : Fragment() {
    private lateinit var tvNama: TextView
    private lateinit var tvLokasi: TextView
    private lateinit var tvDeskripsi: TextView
    private lateinit var ivWisata: ImageView
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .build()

    companion object {
        private const val ARG_WISATA = "wisata"
        private const val UNSPLASH_API_KEY = "gqcKLJb-hwNfedsCBRCwSvSt9yIwlXtTvy33u142OjA"
        private const val UNSPLASH_API_URL = "https://api.unsplash.com/search/photos"
        private const val TAG = "DetailWisataFragment"

        fun newInstance(wisata: Wisata): DetailWisataFragment {
            return DetailWisataFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_WISATA, wisata)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_wisata, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvNama = view.findViewById(R.id.tvNama)
        tvLokasi = view.findViewById(R.id.tvLokasi)
        tvDeskripsi = view.findViewById(R.id.tvDeskripsi)
        ivWisata = view.findViewById(R.id.ivWisata)

        arguments?.getParcelable<Wisata>(ARG_WISATA)?.let { wisata ->
            tvNama.text = wisata.nama
            tvLokasi.text = wisata.lokasi
            tvDeskripsi.text = wisata.deskripsi

            // Load default image first
            loadDefaultImage()
            
            // Then try to fetch from Unsplash
            val searchQuery = "${wisata.nama} ${wisata.lokasi} indonesia tourism"
            Log.d(TAG, "Searching for images with query: $searchQuery")
            fetchImageFromUnsplash(searchQuery)
        }
    }

    private fun fetchImageFromUnsplash(query: String) {
        val encodedQuery = java.net.URLEncoder.encode(query, "UTF-8")
        val url = "$UNSPLASH_API_URL?query=$encodedQuery&per_page=1"
        Log.d(TAG, "Requesting URL: $url")

        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Client-ID $UNSPLASH_API_KEY")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Network error: ${e.message}", e)
                activity?.runOnUiThread {
                    Toast.makeText(context, "Gagal memuat gambar: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        val errorBody = response.body?.string()
                        Log.e(TAG, "API error: ${response.code}, Body: $errorBody")
                        activity?.runOnUiThread {
                            Toast.makeText(context, "Gagal memuat gambar: ${response.code}", Toast.LENGTH_SHORT).show()
                        }
                        return
                    }

                    try {
                        val jsonData = response.body?.string()
                        Log.d(TAG, "Response data: $jsonData")
                        
                        val jsonObject = JSONObject(jsonData)
                        val results = jsonObject.getJSONArray("results")

                        if (results.length() > 0) {
                            val firstImage = results.getJSONObject(0)
                            val imageUrl = firstImage.getJSONObject("urls").getString("regular")
                            Log.d(TAG, "Found image URL: $imageUrl")

                            activity?.runOnUiThread {
                                loadImage(imageUrl)
                            }
                        } else {
                            Log.d(TAG, "No images found for query: $query")
                            activity?.runOnUiThread {
                                Toast.makeText(context, "Tidak ada gambar yang ditemukan", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "JSON parsing error: ${e.message}", e)
                        activity?.runOnUiThread {
                            Toast.makeText(context, "Gagal memuat gambar: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })
    }

    private fun loadImage(imageUrl: String) {
        Log.d(TAG, "Loading image from URL: $imageUrl")
        
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .placeholder(R.drawable.default_wisata)
            .error(R.drawable.default_wisata)
            .timeout(30000)

        try {
            Glide.with(this)
                .load(imageUrl)
                .apply(requestOptions)
                .into(ivWisata)
        } catch (e: Exception) {
            Log.e(TAG, "Glide error: ${e.message}", e)
            Toast.makeText(context, "Gagal memuat gambar: ${e.message}", Toast.LENGTH_SHORT).show()
            loadDefaultImage()
        }
    }

    private fun loadDefaultImage() {
        try {
            Glide.with(this)
                .load(R.drawable.default_wisata)
                .centerCrop()
                .into(ivWisata)
        } catch (e: Exception) {
            Log.e(TAG, "Error loading default image: ${e.message}", e)
        }
    }
} 