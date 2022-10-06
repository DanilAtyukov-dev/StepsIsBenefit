package com.danilatyukov.linkedmoney.data.local.preferences

import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.AppComponent
import com.danilatyukov.linkedmoney.appComponent
import org.osmdroid.util.Distance

class SavedPreference {

    companion object{
        private val editor = App.it().appComponent.editor

        fun setEmail(email: String){
            editor.putString("email", email)
            editor.apply()
            editor.commit()
        }
        fun setUsername(name: String){
            editor.putString("username", name)
            editor.apply()
            editor.commit()
        }
        fun setUid(uid: String){
            editor.putString("uid", uid)
            editor.apply()
            editor.commit()
        }
        fun adViewed(ads: Int){
            //val ads = RetrievedPreference.getAds()
            editor.putInt("ads", ads)
            editor.apply()
            editor.commit()
        }
        fun incrementAllSteps(number: Int){
            val last = RetrievedPreference.Companion.getAllSteps()

            editor.putInt("allSteps", number+last)
            editor.apply()
            editor.commit()
        }
        fun setCurrentSteps(number: Int){
            editor.putInt("steps", number)
            editor.apply()
            editor.commit()
        }
        fun setConfirmSteps(number: Int){
            editor.putInt("confirmSteps", number)
            editor.apply()
            editor.commit()
        }

        fun setSopr(number: Float){
            editor.putFloat("sopr", number)
            editor.apply()
            editor.commit()
        }

        fun setKrv(krv: Int){
            editor.putInt("krv", krv)
            editor.apply()
            editor.commit()
        }

        fun incrementDistanceGPS(distance: Float){
            val last = RetrievedPreference.Companion.getDistanceGPS()

            editor.putFloat("distanceGPS", distance+last)
            editor.apply()
            editor.commit()
        }
        fun incrementAllDistanceGPS(distance: Float){
            val last = RetrievedPreference.Companion.getAllDistanceGPS()

            editor.putFloat("AllDistanceGPS", distance+last)
            editor.apply()
            editor.commit()
        }

        fun setGoogleReferrerId(id: String){
            editor.putString("referrerId", id)
            editor.apply()
            editor.commit()
        }

        fun setReferrerUserId(id: String){
            editor.putString("referrerUID", id)
            editor.apply()
            editor.commit()
        }

        fun setReferrerAndroidId(id: String){
            editor.putString("referrerDID", id)
            editor.apply()
            editor.commit()
        }

        fun setWrittenRefBonusSteps(d: Double) {
            editor.putFloat("wrb", d.toFloat())
            editor.apply()
            editor.commit()
        }
        fun setRefBonus(d: Double) {
            editor.putFloat("refBonus", d.toFloat())
            editor.apply()
            editor.commit()
        }

        fun setStepPrice(stepPrice: String) {
            editor.putString("stepPrice", stepPrice)
            editor.apply()
            editor.commit()
        }

        fun setReferralsNumber(num: Int) {
            editor.putInt("referrals", num)
            editor.apply()
            editor.commit()
        }
    }
}