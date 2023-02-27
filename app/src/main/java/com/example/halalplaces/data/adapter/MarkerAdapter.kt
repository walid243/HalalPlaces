package com.example.halalplaces.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.halalplaces.R
import com.example.halalplaces.databinding.RecyclerItemBinding
import com.google.android.gms.maps.model.MarkerOptions

class MarkerAdapter(private val markers: List<MarkerOptions>):
RecyclerView.Adapter<MarkerAdapter.ViewHolder>()
{
    inner class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        val binding = RecyclerItemBinding.bind(view)
    }

    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return markers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val marker = markers[position]
        with(holder){
            binding.title.text = marker.title
        }
    }
}