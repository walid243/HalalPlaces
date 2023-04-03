package com.example.halalplaces.ui.map

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.halalplaces.data.model.Place
import com.example.halalplaces.data.model.PlaceData
import com.example.halalplaces.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private lateinit var binding : FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.setFragmentResultListener("place", this){id, placeData ->
            val place = placeData.getParcelable<Place>(id)!!
            binding.PlaceName.text = place.name
            binding.PlaceDescription.text = place.description
            if (place.image != null) {
                binding.placeImage.setImageBitmap(
                    BitmapFactory.decodeByteArray(
                        place.image,
                        0,
                        place.image!!.size
                    )
                )
            }

        }
    }


}