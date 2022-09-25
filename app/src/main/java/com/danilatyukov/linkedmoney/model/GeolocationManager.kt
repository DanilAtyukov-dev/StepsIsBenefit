package com.danilatyukov.linkedmoney.model

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.appComponent
import com.danilatyukov.linkedmoney.appContext
import com.danilatyukov.linkedmoney.data.local.geopoints.GeopointEntity
import com.google.android.gms.location.*
import javax.inject.Inject
import kotlin.math.roundToInt


class GeolocationManager {

    init {
        App.it().appComponent.injectGeolocationManager(this)
    }

    // globally declare LocationRequest
    @Inject
    lateinit var locationRequest: LocationRequest

    @Inject
    lateinit var fusedLocationClient: FusedLocationProviderClient

    // globally declare LocationCallback
    private lateinit var locationCallback: LocationCallback


    //start location updates
    fun startLocationUpdates() {
        println("onLocationResult")
        if (ActivityCompat.checkSelfPermission(
                App.it().appContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                App.it().appContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        getLocationUpdates()
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null /* Looper */
        )
    }

    private fun getLocationUpdates() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                if (p0.locations.isNotEmpty()) {
                    // get latest location
                    val location = p0.lastLocation
                    var kmh: Int = -1

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        kmh = (location!!.speedAccuracyMetersPerSecond * 3.6).roundToInt()
                    }

                    val geopointEntity = GeopointEntity(
                        "date",
                        location!!.time.toString(),
                        kmh,
                        location.accuracy,
                        location.longitude,
                        location.latitude
                    )

                    if (location.accuracy <= 20 && kmh <= 110) {
                        Thread {
                            App.it().appComponent.geopointDao.insert(geopointEntity)
                        }.start()
                    }
                }
            }
        }
    }


}