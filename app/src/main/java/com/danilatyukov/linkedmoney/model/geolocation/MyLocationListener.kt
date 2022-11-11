package com.danilatyukov.linkedmoney.model.geolocation

import android.location.Location
import android.location.LocationListener
import android.os.Build
import android.os.Bundle
import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.appComponent
import com.danilatyukov.linkedmoney.data.local.geopoints.GeopointDao
import com.danilatyukov.linkedmoney.data.local.geopoints.GeopointEntity
import kotlin.math.roundToInt

class MyLocationListener: LocationListener {
    val geopointDao: GeopointDao = App.it().appComponent.geopointDao
    var lastLocation: Location? = null

    override fun onLocationChanged(p0: Location) {
        println("MyLocationListener onLocationChanged")

        var kmh: Int = -1

        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd")
        val stf = java.text.SimpleDateFormat("HH:mm:ss")
        val date = java.util.Date(p0.time)
        val dateStr = sdf.format(date)
        val timeStr = stf.format(date)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            kmh = (p0!!.speedAccuracyMetersPerSecond * 3.6).roundToInt()
        }

        val geopointEntity = GeopointEntity(
            dateStr,
            timeStr,
            kmh,
            p0.accuracy,
            p0.longitude,
            p0.latitude
        )
        println("onLocationHandle $geopointEntity")
        if ((p0.accuracy <= 10 && kmh <= 110) || (p0.accuracy == 20f && kmh <= 110) || lastLocation == null) {
            Thread {
                geopointDao.insert(geopointEntity)
                distance(p0)
                lastLocation = p0
            }.start()
        }
    }

    private fun distance(location: Location) {
        val distance = lastLocation?.distanceTo(location)

        distance?.let {App.it().appComponent.appPreferences.incCurrentDistance(it)}
        distance?.let {App.it().appComponent.appPreferences.incAllDistance(it)}
    }

    override fun onProviderDisabled(provider: String) {

    }

    override fun onProviderEnabled(provider: String) {

    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
}