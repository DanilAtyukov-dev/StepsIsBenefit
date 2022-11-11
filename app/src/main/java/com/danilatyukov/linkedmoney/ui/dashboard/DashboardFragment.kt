package com.danilatyukov.linkedmoney.ui.dashboard

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.R
import com.danilatyukov.linkedmoney.databinding.FragmentDashboardBinding
import com.danilatyukov.linkedmoney.ui.InfoDialogFragment

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        /*parentFragmentManager.beginTransaction()
            .add(_binding!!.pedometerFCV.id, PedometerFragment.newInstance())
            .commit()*/

        isGeoPermission()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isGeoPermission(){
        if (ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (App.it().appComponent.appPreferences.isRequestGeolocation)
                InfoDialogFragment(
                    "Уведомление",
                    App.it().getString(R.string.geolocationNotificationText),
                    R.drawable.free_icon_route,
                    geo = true
                ).show(parentFragmentManager.beginTransaction(), "info")
        }
    }
}