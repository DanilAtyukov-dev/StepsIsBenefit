package com.danilatyukov.linkedmoney.ui.dashboard.pedometer

import android.content.Intent
import android.content.SharedPreferences
import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.appContext
import com.danilatyukov.linkedmoney.data.remote.request.StatisticRequestObject
//import com.danilatyukov.linkedmoney.data.local.preferences.RetrievedPreference
import com.danilatyukov.linkedmoney.model.ForegroundService
import com.danilatyukov.linkedmoney.service.MessageApiService
import com.google.android.gms.tasks.OnCompleteListener

class PedometerViewModel : ViewModel(), SharedPreferences.OnSharedPreferenceChangeListener {

    var preferences = App.it().appComponent.appPreferences
    init {
        preferences.preferences.registerOnSharedPreferenceChangeListener(this)
    }


    private val _counter = MutableLiveData<String>().apply {
        value = preferences.currentSteps.toString()
    }
    val counter: LiveData<String> = _counter

    private val _distance = MutableLiveData<String>().apply {
        value = App.roundFloat(preferences.currentDistance / 1000, "#.#").toString()

    }
    val distance: LiveData<String> = _distance


    // TODO: Implement the ViewModel
    private var numSteps: Long = 0
    private var distanceCounter: Float = 0f

    private lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener



    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        numSteps = preferences.currentSteps
        _counter.value = numSteps.toString()

        distanceCounter = preferences.currentDistance
        _distance.value = App.roundFloat(distanceCounter / 1000, "#.#").toString()
    }

    fun stop() {

        val serviceIntent = Intent(App.it().appContext, ForegroundService::class.java)
        App.it().appContext.stopService(serviceIntent)
    }


    override fun onCleared() {
        super.onCleared()
        //unregisterListener()
    }

    fun unregisterListener() {
        // TODO App.it().appComponent.sp.unregisterOnSharedPreferenceChangeListener(listener)
    }
}