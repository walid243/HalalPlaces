package com.example.halalplaces.data.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapViewModel : ViewModel() {
    private val markers = MutableLiveData<MutableList<MarkerOptions>>(mutableListOf())
    private var markerPosition: LatLng? = null
    private var map: GoogleMap? = null

    fun setMap(map: GoogleMap) {
        this.map = map
    }

    fun getMap(): GoogleMap? {
        return map
    }

    fun setMarkerPosition(position: LatLng?){
        markerPosition = position
    }

    fun getMarkerPosition():LatLng? = markerPosition

    fun addAllMarkersToMap() {
        markers.value!!.forEach {
            map?.addMarker(it)
        }
    }

    fun saveMarker(markerOptions: MarkerOptions) {
        if (markerOptions.title != null) {
            val currentMarkers = markers.value!!
            currentMarkers.add(markerOptions)
            markers.postValue(currentMarkers)
        }
    }
}