package com.danilatyukov.linkedmoney.ui.dashboard.map

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.danilatyukov.linkedmoney.R
import com.danilatyukov.linkedmoney.databinding.FragmentWorldMapBinding
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.overlay.Marker

class WorldMapFragment : Fragment() {

    companion object {
        fun newInstance() = WorldMapFragment()
    }

    private lateinit var _binding: FragmentWorldMapBinding
    private val binding get() = _binding

    private lateinit var viewModel: WorldMapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorldMapBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WorldMapViewModel::class.java)
        // TODO: Use the ViewModel
        initializationMap()
    }


    private fun initializationMap() {
        try {
            Configuration.getInstance().load(requireActivity(), PreferenceManager.getDefaultSharedPreferences(requireContext()))
            val mapController = _binding.worldMapView.controller
            //mapController.setCenter(GeoPoint(UserData.latitude, UserData.longitude))
            _binding.worldMapView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
            _binding.worldMapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
            _binding.worldMapView.setMultiTouchControls(true)
            mapController.setZoom(16.0)
            _binding.worldMapView.maxZoomLevel = 18.2
            _binding.worldMapView.minZoomLevel = 3.0
            setUpMyMarker()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setUpMyMarker() {
        val myMarker = Marker(_binding.worldMapView)
        myMarker.icon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_location_pin)
        /*myMarker.setPosition(
            GeoPoint(
                UserData.latitude,
                UserData.longitude
            )
        ) //первые данные о местоположении пользователя*/
        _binding.worldMapView.overlays.add(myMarker)
    }
}