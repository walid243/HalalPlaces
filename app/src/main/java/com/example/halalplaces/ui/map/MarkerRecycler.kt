package com.example.halalplaces.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.halalplaces.data.adapter.MarkerAdapter
import com.example.halalplaces.data.interfaces.ClickInterface
import com.example.halalplaces.data.map.MapViewModel
import com.example.halalplaces.data.model.Place
import com.example.halalplaces.databinding.FragmentMarkerRecyclerBinding


class MarkerRecycler : Fragment(), ClickInterface {
    private lateinit var markerAdapter: MarkerAdapter
    private lateinit var layoutManager: LayoutManager
    private lateinit var binding: FragmentMarkerRecyclerBinding
    private val mapViewModel: MapViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMarkerRecyclerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        markerAdapter = MarkerAdapter(mapViewModel.getAllMarkers(), this)
        layoutManager = LinearLayoutManager(context)

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = this@MarkerRecycler.layoutManager
            adapter = markerAdapter
        }
    }

    override fun onClick(place: Place) {
        parentFragmentManager.setFragmentResult("place", bundleOf("place" to place))
        val action = MarkerRecyclerDirections.actionMarkerRecyclerFragmentToDetailFragment()
            findNavController().navigate(action)
    }
}