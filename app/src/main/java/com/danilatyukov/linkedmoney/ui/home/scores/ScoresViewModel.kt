package com.danilatyukov.linkedmoney.ui.home.scores

import android.content.SharedPreferences
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.appComponent
import com.danilatyukov.linkedmoney.data.vo.UserVO

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.math.BigDecimal

class ScoresViewModel : ViewModel(), SharedPreferences.OnSharedPreferenceChangeListener {
    // TODO: Implement the ViewModel
    lateinit var userDetails: UserVO

    init {
        App.it().appComponent.appPreferences.preferences.registerOnSharedPreferenceChangeListener(
            this
        )
        userDetails = App.it().appComponent.appPreferences.userDetails

    }

    private val _confirmSteps = MutableLiveData<String>().apply {
        value = userDetails.allSteps.toString()
    }
    val confirmSteps: LiveData<String> = _confirmSteps

    private val _allMoney = MutableLiveData<String>().apply {
        value = userDetails.allMoney.toString()
    }
    val allMoney: LiveData<String> = _allMoney

    private val _allGpsDistance = MutableLiveData<String>().apply {
        value = App.roundFloat(App.it().appComponent.appPreferences.allDistance / 1000, "#.#")
    }
    val allGpsDistance: LiveData<String> = _allGpsDistance

    private val _stepPrice = MutableLiveData<String>().apply {
        value = userDetails.stepCost.toString()
    }
    val stepPrice: LiveData<String> = _stepPrice

    private val _adLoaded = MutableLiveData<Int>().apply {
        value = View.GONE
    }
    val adLoaded: LiveData<Int> = _adLoaded

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        if (p0 == null) return
        _allGpsDistance.value = App.roundFloat(p0.getFloat("ALL_DISTANCE", 0f) / 1000, "#.#")
        _confirmSteps.value = p0.getLong("ALL_STEPS", 0).toString()
        _stepPrice.value = p0.getFloat("STEP_COST", 0.0001f).toString()
        _allMoney.value = p0.getFloat("ALL_MONEY", 0.0f).toString()
         _adLoaded.value = p0.getInt("AD_LOADED", View.GONE)
    }
}