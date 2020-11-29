package com.example.covid19statsapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.covid19statsapp.R
import com.example.covid19statsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        /**
         *Bottom Navigation with action bar
         * */
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(
                item, Navigation.findNavController(this,
                    R.id.nav_host_fragment
                )
            )
        }

        val navController = this.findNavController(R.id.nav_host_fragment)

        binding.bottomNavigation.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.summary,
                R.id.regions,
                R.id.prevention,
                R.id.developer
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

    }
}