package com.example.halalplaces.ui.map

import android.text.Editable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.halalplaces.R
import com.example.halalplaces.data.map.MapViewModel
import com.example.halalplaces.databinding.FragmentAddMarkerBinding
import com.google.android.gms.maps.model.LatLng

class AddMarkerFragment : Fragment() {

    private lateinit var binding : FragmentAddMarkerBinding
    private val mapViewModel : MapViewModel by activityViewModels()

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: android.os.Bundle?
    ): android.view.View? {
        binding = FragmentAddMarkerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val markerData = mapViewModel.getMarkerData()
        if (markerData?.name == "") binding.etMarkerLatitude.setText("${markerData.coordinates.latitude},${markerData.coordinates.longitude}")
        binding.btnSubmit.setOnClickListener {
            saveMarker()
        }

        if (!::binding.isInitialized) binding = FragmentAddMarkerBinding.bind(view)
    }

    private fun saveMarker() {
        val name = binding.etMarkerName.text.toString()
        val coordinates = binding.etMarkerLatitude.text.toString().split(',')
        val coordinate = LatLng(coordinates[0].toDouble(), coordinates[1].toDouble())
        val markerData = com.example.halalplaces.data.model.MarkerData(name, coordinate)
        mapViewModel.setMarkerData(markerData)

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MapsFragment(), "map")
            .addToBackStack(tag)
            .commit()
    }
}
