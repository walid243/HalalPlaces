package com.example.halalplaces.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.halalplaces.R
import com.example.halalplaces.data.AppViewModel
import com.example.halalplaces.data.map.MapViewModel
import com.example.halalplaces.databinding.FragmentMapsBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

const val REQUEST_CODE_LOCATION = 100

@SuppressLint("MissingPermission")
class MapsFragment : Fragment(), OnMapReadyCallback {
    lateinit var binding : FragmentMapsBinding
    private val mapViewModel : MapViewModel by activityViewModels()
    private lateinit var map: GoogleMap
    private fun createMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapViewModel.setMap(googleMap)
        map = mapViewModel.getMap() ?: googleMap
        map.setOnMapLongClickListener {coordinates ->
            mapViewModel.setMarkerPosition(coordinates)
            addNewMarker()
        }
        enableLocation()
        mapViewModel.addAllMarkersToMap()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        createMap()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addMarker.setOnClickListener {
            addNewMarker()
        }

    }

    private fun addNewMarker() {
        val action = MapsFragmentDirections.actionMapsFragmentToAddMarkerFragment()
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        if (!::map.isInitialized) return
        if (!isLocationPermissionGranted()) {
            map.isMyLocationEnabled = false
            Toast.makeText(
                requireContext(), "Accepta els permisos de geolocalització",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun enableLocation() {
        if (!::map.isInitialized) return
        if (isLocationPermissionGranted()) {
            map.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(
                requireContext(),
                "Ves a la pantalla de permisos de l’aplicació i habilita el de Geolocalització",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_LOCATION -> if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                map.isMyLocationEnabled = true
            } else {
                Toast.makeText(
                    requireContext(), "Accepta els permisos de geolocalització",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


}