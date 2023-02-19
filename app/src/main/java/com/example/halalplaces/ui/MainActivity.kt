package com.example.halalplaces.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.halalplaces.R
import com.example.halalplaces.ui.login.LoginFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toLoginFragment()
    }

    fun toLoginFragment() {
        val loginFragment = LoginFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, loginFragment)
            .commit()
    }
}