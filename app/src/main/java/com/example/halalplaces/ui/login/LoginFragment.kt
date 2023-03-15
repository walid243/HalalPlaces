package com.example.halalplaces.ui.login

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.halalplaces.data.AppViewModel
import com.example.halalplaces.data.DataBase
import com.example.halalplaces.data.login.SessionManager
import com.example.halalplaces.databinding.FragmentLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val appViewModel: AppViewModel by activityViewModels()
    private val sessionManager = SessionManager()
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


        username.addTextChangedListener {
            isValidEmail = checkEmail(it.toString())
            enableLoginButton()
        }
        password.addTextChangedListener {
            isValidPassword = checkPasswordLength(it?.length ?: 0)
            enableLoginButton()
        }

        login.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                sessionManager.login(username.text.toString(), password.text.toString())
                println("${DataBase.app.currentUser?.id} <-------------")
                appViewModel.setIsLoggedIn()
                withContext(Dispatchers.Main) { toSplashScreen() }
            }
        }
    }

    fun toSplashScreen() {
        val action = LoginFragmentDirections.actionLoginFragmentToSplashScreen()
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