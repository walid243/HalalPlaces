package com.example.halalplaces.ui.login

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.halalplaces.data.login.LoginManager
import com.example.halalplaces.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val loginManager = LoginManager()
    private var isValidEmail: Boolean = false
    private var isValidPassword: Boolean = false

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


        username.addTextChangedListener{
                isValidEmail = checkEmail(it.toString())
        }
        password.addTextChangedListener {
            isValidPassword = checkPasswordLength(it.length)
            }
            enableLoginButton()
        }

        login.setOnClickListener {
            toMapsFragment()
        }
    }

    fun toMapsFragment() {
        val action = LoginFragmentDirections.actionLoginFragmentToMapsFragment()
        findNavController().navigate(action)
    }

    private fun checkEmail(data: String): Boolean {
        Patterns.EMAIL_ADDRESS.matcher(data)
            .let {
                return it.matches()
            }
    }

    private fun checkPasswordLength(length: Int): Boolean {
        return length > 5
    }

    private fun enableLoginButton() {
        binding.login.isEnabled = isValidEmail && isValidPassword
    }

}