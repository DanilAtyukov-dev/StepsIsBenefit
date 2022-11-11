package com.danilatyukov.linkedmoney.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import com.danilatyukov.linkedmoney.data.vo.UserVO

class AppPreferences private constructor() {
    lateinit var preferences: SharedPreferences

    companion object {
        private val fileName = "APP_PREFERENCES"
        fun create(context: Context): AppPreferences {
            val appPreferences = AppPreferences()
            appPreferences.preferences = context.getSharedPreferences(fileName, 0)
            return appPreferences
        }
    }

    val accessToken: String? get() = preferences.getString("ACCESS_TOKEN", "null")

    fun storeAccessToken(accessToken: String) {
        preferences.edit().putString("ACCESS_TOKEN", accessToken).apply()
    }

    val userDetails: UserVO
        get(): UserVO {
            return UserVO(
                preferences.getLong("ID", 0),
                preferences.getString("USERNAME", "Visitor"),
                preferences.getLong("REFERRER_ID", 0),
                preferences.getFloat("ALL_MONEY", 0f),
                preferences.getFloat("STEP_COST", 0f),
                preferences.getFloat("REF_BONUS", 0f),
                preferences.getLong("REFERRALS", 0),
                preferences.getLong("ALL_STEPS", 0),
                preferences.getString("ACCOUNT_STATUS", "activated"),
                preferences.getString("PRIVILEGE_STATUS", "default"),
                preferences.getString("CREATED_AT", "null")
            )
        }

    fun storeUserDetails(user: UserVO) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putLong("ID", user.id).apply()
        editor.putString("USERNAME", user.email).apply()
        editor.putFloat("ALL_MONEY", user.allMoney)
        editor.putFloat("STEP_COST", user.stepCost!!)
        editor.putFloat("REF_BONUS", user.allRefBonus)
        editor.putLong("REFERRALS", user.allReferrals)
        editor.putLong("ALL_STEPS", user.allSteps)
        editor.putString("ACCOUNT_STATUS", user.accountStatus).apply()
        editor.putString("PRIVILEGE_STATUS", user.privilegeStatus).apply()
        editor.putString("CREATED_AT", user.createdAt).apply()
    }

    fun clear() {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }

    val isRequestGeolocation: Boolean get() = preferences.getBoolean("IS_REQUEST_GEOLOCATION", true)

    fun setRequestGeolocation(why: Boolean) {
        val editor: SharedPreferences.Editor = preferences.edit()

        editor.putBoolean("IS_REQUEST_GEOLOCATION", why)
    }

    val confirmSteps: Long get() = preferences.getLong("ALL_STEPS", 0)
    fun incConfirmSteps(num: Long) {
        preferences.edit().putLong("ALL_STEPS", confirmSteps+num).apply()
    }

    val currentSteps: Long get() = preferences.getLong("CURRENT_STEPS", 0)
    fun incCurrentSteps(num: Long) {
        preferences.edit().putLong("CURRENT_STEPS", currentSteps+num).apply()
    }
    fun clearCurrentSteps() {
        preferences.edit().putLong("CURRENT_STEPS", 0).apply()
    }

    val sentSteps: Long
        get() = preferences.getLong("SENT_STEPS", 0)
    fun incSentSteps(num: Long) {
        preferences.edit().putLong("SENT_STEPS", sentSteps+num).apply()
    }
    fun clearSentSteps() {
        preferences.edit().putLong("SENT_STEPS", 0).apply()
    }

    val currentAds: Long get() = preferences.getLong("CURRENT_ADS", 0)

    fun incCurrentAds(num: Long) {
        preferences.edit().putLong("CURRENT_ADS", currentAds+num).apply()
    }
    fun clearCurrentAds() {
        preferences.edit().putLong("CURRENT_ADS", 0).apply()
    }

    val currentDistance: Float get() = preferences.getFloat("CURRENT_DISTANCE", 0f)

    fun incCurrentDistance(num: Float) {
        preferences.edit().putFloat("CURRENT_DISTANCE", currentDistance+num).apply()
    }
    fun clearCurrentDistance() {
        preferences.edit().putFloat("CURRENT_DISTANCE", 0f).apply()
    }


    val allDistance: Float get() = preferences.getFloat("ALL_DISTANCE", 0f)
    fun incAllDistance(num: Float) {
        preferences.edit().putFloat("ALL_DISTANCE", allDistance+num).apply()
    }

    val adLoaded: Int get() = preferences.getInt("AD_LOADED", View.GONE)
    fun changeAdLoaded(bool: Boolean){
        if (bool) preferences.edit().putInt("AD_LOADED", View.VISIBLE).apply()
        else preferences.edit().putInt("AD_LOADED", View.GONE).apply()
    }


}

