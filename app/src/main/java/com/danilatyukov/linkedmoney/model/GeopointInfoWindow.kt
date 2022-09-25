package com.danilatyukov.linkedmoney.model

import android.widget.TextView
import com.danilatyukov.linkedmoney.R
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow

class GeopointInfoWindow(mapView: MapView, private val time: String, private val speed: String) :
    MarkerInfoWindow(R.layout.geopoint_marker_info_window, mapView) {

    override fun onOpen(item: Any?) {

        val timeView = mView.findViewById<TextView>(R.id.time_tv)
        timeView.text = time
        val speedView = mView.findViewById<TextView>(R.id.speed_tv)
        speedView.text = speed
    }
}