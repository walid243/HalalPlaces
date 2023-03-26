package com.example.halalplaces.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.halalplaces.R
import com.example.halalplaces.data.AppViewModel
import com.example.halalplaces.data.DataBase
import com.example.halalplaces.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarMenu

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navHostFragment.addOnLayoutChangeListener { _: View, _: Int, _: Int, _: Int, _: Int, _: Int, _: Int, _: Int, _: Int ->
            val supportActionBar: ActionBar? = this.supportActionBar
            if (navController.currentDestination?.id == R.id.loginFragment) {
                supportActionBar?.hide()
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            } else {
                supportActionBar?.show()
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
        }

        binding.logout.setOnClickListener {
            appViewModel.sessionManager.logOut()
            appViewModel.setLoggedOut()
            closeDrawer()
            toLoginFragment()
        }
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val drawerLayout = binding.drawerLayout

        binding.navView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mapsFragment,
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
//        createActionBarDrawerToggle()

    }

    override fun onSupportNavigateUp(): Boolean {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun toLoginFragment() {
        navController.navigate(R.id.loginFragment)
    }

    private fun closeDrawer() {
        println("${binding.navView.isLaidOut}<------------------>${binding.drawerLayout.isOpen}")
        if (binding.navView.isLaidOut) {
            println("Drawer is open<------------------")
            binding.drawerLayout.close()
        }

    }

    private fun createActionBarDrawerToggle() {
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.open,
            R.string.close
        )

        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        actionBarDrawerToggle.syncState()
    }


}