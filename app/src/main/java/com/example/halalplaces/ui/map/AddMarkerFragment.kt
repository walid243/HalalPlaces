package com.example.halalplaces.ui.map

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.halalplaces.R
import com.example.halalplaces.data.DataBase
import com.example.halalplaces.data.map.MapViewModel
import com.example.halalplaces.data.model.MarkerData
import com.example.halalplaces.databinding.FragmentAddMarkerBinding
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class AddMarkerFragment : Fragment() {

    private lateinit var binding: FragmentAddMarkerBinding
    private val mapViewModel: MapViewModel by activityViewModels()

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: android.os.Bundle?
    ): android.view.View {
        binding = FragmentAddMarkerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val coordinates = mapViewModel.getMarkerPosition()
        if (coordinates != null) binding.etMarkerLatitude.setText("${coordinates.latitude},${coordinates.longitude}")
        binding.btnSubmit.setOnClickListener {
            saveMarker()
            toMapsFragment()
        }

        if (!::binding.isInitialized) binding = FragmentAddMarkerBinding.bind(view)
    }

    private fun saveMarker() {
        val name = binding.etMarkerName.text.toString()
        val coordinates = binding.etMarkerLatitude.text.toString().split(',')
        val latitude = coordinates[0].toDouble()
        val longitude = coordinates[1].toDouble()
        val markerData = MarkerData(
            name = name,
            latitude = latitude,
            longitude = longitude,
            ownerId = DataBase.currentUser!!.id
        )


        mapViewModel.saveMarker(markerData)
        mapViewModel.setMarkerPosition(null)
    }

    private fun toMapsFragment() {
        val action = AddMarkerFragmentDirections.actionAddMarkerFragmentToMapsFragment()
        findNavController().navigate(action)
    }
}
