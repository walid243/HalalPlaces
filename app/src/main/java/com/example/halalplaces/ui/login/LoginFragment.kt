package com.example.halalplaces.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.halalplaces.R
import com.example.halalplaces.databinding.FragmentLoginBinding
import com.example.halalplaces.ui.map.MapsFragment

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
    return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = binding.username
        val password = binding.password
        val login = binding.login

        login.setOnClickListener {
            toMapsFragment()
        }
    }

    fun toMapsFragment() {
        val action = LoginFragmentDirections.actionLoginFragmentToMapsFragment()
        findNavController().navigate(action)
    }


}