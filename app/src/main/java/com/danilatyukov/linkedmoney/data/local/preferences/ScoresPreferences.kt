package com.danilatyukov.linkedmoney.data.local.preferences

import android.content.Context
import android.content.SharedPreferences

class ScoresPreferences {

    lateinit var preferences: SharedPreferences

    companion object{
        private const val fileName = "SCORES"
        fun create(context: Context): ScoresPreferences {
            val scoresPreferences = ScoresPreferences()
            scoresPreferences.preferences = context.getSharedPreferences(fileName, 0)
            return scoresPreferences
        }
    }



/*    val stepsNum: Long
        get() = preferences.getLong("STEPS_NUM", 0)

    fun incrementStepsNum(num: Long) {
        preferences.edit().putLong("STEPS_NUM", stepsNum+num).apply()
    }*/

    /*val adNum: Long
        get() = preferences.getLong("ADS_NUM", 0)

    fun incrementAdNum(num: Long) {
        preferences.edit().putLong("ADS_NUM", adNum+num).apply()
    }*/

    /*val stepCost: Float
    get() = preferences.getFloat("STEP_COST", 0.001f)

    fun setStepCost(cost: Float) {
        preferences.edit().putFloat("STEP_COST", cost).apply()
    }*/

    /*val allSteps: Long
        get() = preferences.getLong("ALL_STEPS", 0)
    fun incrementAllSteps(num: Long) {
        preferences.edit().putLong("ALL_STEPS", allSteps+num).apply()
    }*/


}