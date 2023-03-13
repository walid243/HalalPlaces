package com.example.halalplaces.data.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.halalplaces.data.DataBase
import com.example.halalplaces.data.model.MarkerData
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.flow.map

class MapViewModel : ViewModel() {
    private var markers = MutableLiveData<MutableList<MarkerData>>(mutableListOf())
    private var markerPosition: LatLng? = null
    private var map: GoogleMap? = null

    init {
        getMarkersFromDB()
    }
    fun setMap(map: GoogleMap) {
        this.map = map
    }

    fun getMap(): GoogleMap? {
        return map
    }

    fun getAllMarkers() = markers.value!!

    fun setMarkerPosition(position: LatLng?) {
        markerPosition = position
    }

    fun getMarkerPosition(): LatLng? = markerPosition

    fun addAllMarkersToMap() {
        markers.value!!.forEach {
            map?.addMarker(
                MarkerOptions()
                    .position(LatLng(it.latitude, it.longitude))
                    .title(it.name)
            )
        }
    }

    fun saveMarker(markerData: MarkerData) {
        val currentMarkers = markers.value!!
        currentMarkers.add(markerData)
        markers.postValue(currentMarkers)
        DataBase.insert(markerData)
    }

    private fun getMarkersFromDB(){
        markers.value = DataBase.getAllMarkers().toMutableList()
    }
}