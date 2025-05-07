package com.example.wisataindonesia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wisataindonesia.R
import com.example.wisataindonesia.adapter.WisataAdapter
import com.example.wisataindonesia.model.Wisata
import com.example.wisataindonesia.MainActivity

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WisataAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvWisata)
        recyclerView.layoutManager = LinearLayoutManager(context)
        
        val wisataList = (activity as? MainActivity)?.getWisataList() ?: emptyList()
        adapter = WisataAdapter(wisataList.toMutableList()) { wisata ->
            showDetailFragment(wisata)
        }
        recyclerView.adapter = adapter
    }

    private fun showDetailFragment(wisata: Wisata) {
        val detailFragment = DetailWisataFragment.newInstance(wisata)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, detailFragment)
            .addToBackStack(null)
            .commit()
    }
} 