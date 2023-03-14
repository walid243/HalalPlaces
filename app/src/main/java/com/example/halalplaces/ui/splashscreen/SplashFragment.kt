package com.example.halalplaces.ui.splashscreen

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.halalplaces.data.AppViewModel
import com.example.halalplaces.data.DataBase
import com.example.halalplaces.databinding.FragmentSplashScreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {
    private lateinit var binding : FragmentSplashScreenBinding
    private val viewModel : AppViewModel by activityViewModels()

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
        viewModel.isLoggedIn.observe(viewLifecycleOwner ){
            if (it) {
//                while (DataBase.realm == null ){
//                }
                CoroutineScope(Dispatchers.Default).launch {
                    awaitAll(DataBase.subscribeToRealm())
                    val action = SplashFragmentDirections.actionSplashScreenToMapsFragment()
                    findNavController().navigate(action)
                }
            } else {
                val action = SplashFragmentDirections.actionSplashScreenToLoginFragment()
                findNavController().navigate(action)
            }
        }
    }

}