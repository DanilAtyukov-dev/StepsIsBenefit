package com.danilatyukov.linkedmoney

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.danilatyukov.linkedmoney.data.RetrievedPreference
import com.danilatyukov.linkedmoney.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : BaseActivity() {

    private lateinit var amb: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        amb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(amb.root)

        val navView: BottomNavigationView = amb.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        
        //setActionBar(navController)

        navView.setupWithNavController(navController)
        showToast("${RetrievedPreference.getEmail()}, ${RetrievedPreference.getUsername()}")
    }

    /*private fun setActionBar(c: NavController){
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(c, appBarConfiguration)
    }*/
}