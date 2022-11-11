package com.danilatyukov.linkedmoney

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings
import com.my.target.ads.InterstitialAd
import com.my.target.ads.InterstitialAd.InterstitialAdListener
import java.text.DecimalFormat


class App : Application() {
    lateinit var appComponent: AppComponent
    lateinit var context: Context
    public var mainActivity: MainActivity? = null

    companion object {


        val version: Long = 6
        lateinit var ANDROID_ID: String

        lateinit var app: App
        fun it(): App {
            return app
        }

        fun roundFloat(f: Float, pattern: String): String {
            val df = DecimalFormat(pattern)
            return df.format(f)
        }

        val baseUrl = "http://stepsisbenefit.ru"
        //val referralLink = "$baseUrl/referral/appLink?referrer=${it().appComponent.appPreferences.userDetails.id}"
    }

    @SuppressLint("HardwareIds")
    override fun onCreate() {
        super.onCreate()

        app = this
        appComponent = DaggerAppComponent.create()
        context = this

        //appComponent.sp.registerOnSharedPreferenceChangeListener(listener)

        ANDROID_ID = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID);

    }




fun vibratePhone() {
    val vibrator = getSystemService( Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(100)
    }
}






private val listener =
    OnSharedPreferenceChangeListener { sharedPreferences, key ->

    }

fun unregisterListener() {
    //appComponent.sp.unregisterOnSharedPreferenceChangeListener(listener)
}

}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }


val Context.appContext: Context
    get() = when (this) {
        is App -> context
        else -> this.applicationContext.appContext
    }

/*
var Context.mainActivity: MainActivity
    get() = when (this) {
        is App -> mainActivity
        else -> this.applicationContext.mainActivity
    }*/
