package com.danilatyukov.linkedmoney.ui.home.referralData

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.App.Companion.it
import com.danilatyukov.linkedmoney.data.vo.UserVO

class ReferralDataViewModel : ViewModel(), SharedPreferences.OnSharedPreferenceChangeListener {
    // TODO: Implement the ViewModel
    lateinit var userDetails: UserVO

    init {
        App.it().appComponent.appPreferences.preferences.registerOnSharedPreferenceChangeListener(this)
        userDetails = App.it().appComponent.appPreferences.userDetails
    }

    private val _referralLink = MutableLiveData<String>().apply {
        value = App.baseUrl+"/referral/appLink?referrer=${it().appComponent.appPreferences.userDetails.id}"//ReferralProgram.referralLink
    }
    var referralLink: LiveData<String> = _referralLink

    private val _referralNum = MutableLiveData<String>().apply {
        value = userDetails.allReferrals.toString()
    }
    var referralNum: LiveData<String> = _referralNum

    private val _referralBonus = MutableLiveData<String>().apply {
        value = App.roundFloat(userDetails.allRefBonus, "#.#####")
    }
    var referralBonus: LiveData<String> = _referralBonus

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        _referralNum.value = p0!!.getLong("REFERRALS", 0).toString()
        _referralBonus.value = App.roundFloat(p0.getFloat("REF_BONUS", 0f), "#.#####")
    }
}