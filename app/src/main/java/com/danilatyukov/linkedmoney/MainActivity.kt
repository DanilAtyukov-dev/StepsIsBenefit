package com.danilatyukov.linkedmoney

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.danilatyukov.linkedmoney.data.local.preferences.RetrievedPreference
import com.danilatyukov.linkedmoney.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : BaseActivity() {

    private val MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

    private lateinit var amb: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        amb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(amb.root)

        val navView: BottomNavigationView = amb.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        
        //setActionBar(navController)
        //actionBar?.hide()

        geolocationRequest()

        navView.setupWithNavController(navController)
        showToast("${RetrievedPreference.getEmail()}, ${RetrievedPreference.getUsername()}")
    }


    fun geolocationRequest() { //запрос геолокации
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
                )
            }
        }
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