package com.danilatyukov.linkedmoney.data.local.preferences


import android.content.SharedPreferences
import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.AppComponent
import com.danilatyukov.linkedmoney.appComponent


class RetrievedPreference {
    companion object{
        private val sp: SharedPreferences = App.it().appComponent.sp

        fun getEmail(): String{
            return sp.getString("email", "unauthorized").toString()
        }
        fun getUsername(): String{
            return sp.getString("username", "unauthorized").toString()
        }
        fun getUid(): String{
            return sp.getString("uid", "unauthorized").toString()
        }
        fun getAds(): Int{
            return sp.getInt("ads", 0)
        }
    }
}