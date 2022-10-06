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

        fun getAllSteps(): Int{
            return sp.getInt("allSteps", 0)
        }

        fun getSopr(): Float{
            return sp.getFloat("sopr", 0.01f)
        }
        fun getWrittenRefBonus():Float{
            return sp.getFloat("wrb", 0f)
        }

        fun getRefBonus():Float{
            return sp.getFloat("refBonus", 0f)
        }

        fun getCurrentSteps(): Int{
            return sp.getInt("steps", 0)
        }
        fun getDistanceGPS(): Float{
            return sp.getFloat("distanceGPS", 0f)
        }
        fun getAllDistanceGPS(): Float{
            return sp.getFloat("AllDistanceGPS", 0f)
        }

        fun getReferrerId(): String{
            return sp.getString("referrerId", "non").toString()
        }

        fun getStepPrice(): String{
            return sp.getString("stepPrice", "0.0").toString()
        }


        fun getReferrerUserId(): String{
            return sp.getString("referrerUID", "non").toString()
        }

        fun getReferrerAndroidId(): String{
            return sp.getString("referrerDID", "non").toString()
        }

        fun getConfirmSteps(): Int {
            return sp.getInt("confirmSteps", 0)
        }

        fun getAdViewed(): Int {
            return sp.getInt("ads", 0)
        }

        fun getKrv(): Int {
            return sp.getInt("krv", 10)
        }

        fun getRefNum(): Int {
            return sp.getInt("referrals", 0)
        }
    }
}