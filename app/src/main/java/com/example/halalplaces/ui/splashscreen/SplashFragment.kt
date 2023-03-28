package com.example.halalplaces.ui.splashscreen

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.ThumbnailUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.halalplaces.R
import com.example.halalplaces.data.AppViewModel
import com.example.halalplaces.data.DataBase
import com.example.halalplaces.databinding.FragmentSplashScreenBinding
import kotlinx.coroutines.*

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashScreenBinding
    private val viewModel: AppViewModel by activityViewModels()

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: android.os.Bundle?
    ): android.view.View {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isLoggedIn.observe(viewLifecycleOwner) {
            var action: NavDirections = SplashFragmentDirections.actionSplashScreenToLoginFragment()

            if (it) {
                CoroutineScope(Dispatchers.Default).launch {
                    DataBase.subscribeToRealmAsync().await()
                    withContext(Dispatchers.Main) {

                        val userData = DataBase.getUserData()
                        if (userData != null) {
                            requireActivity().findViewById<TextView>(R.id.username).text =
                                userData.name
                            if (userData.avatar != null) {
                                val userAvatar = BitmapFactory.decodeByteArray(
                                    userData.avatar,
                                    0,
                                    userData.avatar!!.size
                                )
                                requireActivity().findViewById<ImageView>(R.id.userAvatar)
                                    .setImageBitmap(userAvatar)
                            }
                        }

                        //Stop doing something
                        action = SplashFragmentDirections.actionSplashScreenToMapsFragment()
                        findNavController().navigate(action)
                    }

                }
            } else {
                findNavController().navigate(action)
            }
        }
    }

}