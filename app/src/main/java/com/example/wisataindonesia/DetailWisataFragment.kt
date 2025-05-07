package com.example.wisataindonesia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.wisataindonesia.model.Wisata

class DetailWisataFragment : Fragment() {
    private lateinit var tvNama: TextView
    private lateinit var tvLokasi: TextView
    private lateinit var tvDeskripsi: TextView
    private lateinit var ivWisata: ImageView

    companion object {
        private const val ARG_WISATA = "wisata"

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

            Glide.with(this)
                .load(wisata.imageUrl)
                .centerCrop()
                .into(ivWisata)
        }
    }
} 