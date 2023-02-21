package com.example.halalplaces.data.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.halalplaces.data.model.MarkerData
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MarkerOptions

class MapViewModel : ViewModel() {
    private val markers = MutableLiveData<MutableList<MarkerOptions>>(mutableListOf())
    private var newMarker: MarkerOptions? = null
    private var markerData: MarkerData? = null
    private var map: GoogleMap? = null

    fun setMap(map: GoogleMap) {
        this.map = map
    }

    fun getMap(): GoogleMap? {
        return map
    }

    fun setMarkerData(data: MarkerData) {
        markerData = data
    }

    fun getMarkerData(): MarkerData? = markerData

    fun setNewMarker(data: MarkerOptions?) {
        newMarker = data
    }

    fun getNewMaker(): MarkerOptions? = newMarker

    fun addMarker() {
        val coordinates = markerData!!.coordinates
        val tag = markerData!!.name
        val marker = MarkerOptions().position(coordinates).title(tag)
        newMarker = marker
    }

    fun addMarkerToMap() {
        val marker = newMarker!!
        map?.addMarker(marker)
    }

    fun addAllMarkersToMap() {
        markers.value!!.forEach {
            map?.addMarker(it)
        }
    }

    fun saveMarker() {
        if (newMarker != null) {
            val marker = newMarker!!
            val currentMarkers = markers.value!!
            currentMarkers.add(marker)
            markers.postValue(currentMarkers)
            setNewMarker(null)
        }
    }
}