package com.danilatyukov.linkedmoney.model.geolocation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.appComponent
import javax.inject.Inject


class GeolocationManager {
    @Inject
    lateinit var locationManager: LocationManager

    @Inject
    lateinit var context: Context

    var locationListener = MyLocationListener()

    init {
        App.it().appComponent.injectGeolocationManager(this)
    }

    //start location updates
    fun start() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            10000,
            25f,
            locationListener
        )
        locationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            90000,
            0f,
            locationListener
        )
    }

    fun stop() {
        locationManager.removeUpdates(locationListener)
    }
}