package com.example.halalplaces.ui.splashscreen

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
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
                //Do Something
                println("Cargando <---------------")
                CoroutineScope(Dispatchers.Default).launch {
                    DataBase.subscribeToRealmAsync().await()
                    withContext(Dispatchers.Main) {
                        println("Terminó de cargar <------------")
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