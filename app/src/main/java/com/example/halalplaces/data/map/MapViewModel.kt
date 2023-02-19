package com.example.halalplaces.data.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.halalplaces.data.model.Marker
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MarkerOptions

class MapViewModel: ViewModel()  {
    private val data = MutableLiveData<Marker>()
    private var map: GoogleMap? = null

    fun setMap(map: GoogleMap) {
        this.map = map
    }

    fun getMap(): GoogleMap? {
        return map
    }

    fun addMarker(markerData: Marker) {
        val marker = MarkerOptions().position(markerData.coordinates).title(markerData.name)
        map?.addMarker(marker)
        data.postValue(markerData)
    }
}