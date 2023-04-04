package com.example.halalplaces.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.halalplaces.R
import com.example.halalplaces.data.interfaces.ClickInterface
import com.example.halalplaces.data.model.Place
import com.example.halalplaces.data.model.PlaceData
import com.example.halalplaces.databinding.RecyclerItemBinding

class MarkerAdapter(private val placeDataList: List<PlaceData>, private val listener: ClickInterface):
RecyclerView.Adapter<MarkerAdapter.ViewHolder>()
{
    inner class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        val binding = RecyclerItemBinding.bind(view)
        fun setListener(place: Place) {
            binding.root.setOnClickListener {
                listener.onClick(place)
            }
        }
    }

    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return placeDataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val placeData = placeDataList[position]
        val place = Place(placeData.name, placeData.description, placeData.image)
        with(holder){
            setListener(place)
            binding.title.text = place.name
        }
    }
}