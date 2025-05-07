package com.example.wisataindonesia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wisataindonesia.R
import com.example.wisataindonesia.model.Wisata

class WisataAdapter(
    private val wisataList: MutableList<Wisata> = mutableListOf(),
    private val onItemClick: (Wisata) -> Unit
) : RecyclerView.Adapter<WisataAdapter.WisataViewHolder>() {

    class WisataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.tvNama)
        val tvLokasi: TextView = view.findViewById(R.id.tvLokasi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WisataViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wisata, parent, false)
        return WisataViewHolder(view)
    }

    override fun onBindViewHolder(holder: WisataViewHolder, position: Int) {
        val wisata = wisataList[position]
        holder.tvNama.text = wisata.nama
        holder.tvLokasi.text = wisata.lokasi
        holder.itemView.setOnClickListener { onItemClick(wisata) }
    }

    override fun getItemCount() = wisataList.size

    fun updateData(newList: List<Wisata>) {
        wisataList.clear()
        wisataList.addAll(newList)
        notifyDataSetChanged()
    }

    fun addWisata(wisata: Wisata) {
        wisataList.add(wisata)
        notifyItemInserted(wisataList.size - 1)
    }
} 