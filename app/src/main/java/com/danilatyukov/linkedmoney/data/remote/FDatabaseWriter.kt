package com.danilatyukov.linkedmoney.data.remote

import android.provider.Settings
import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.appComponent
import com.danilatyukov.linkedmoney.data.local.preferences.RetrievedPreference
import com.google.android.gms.tasks.Task
import com.google.firebase.database.ServerValue


class FDatabaseWriter() {
    companion object {
        fun writeSteps(num: Long): Task<Void> {
            val uid = RetrievedPreference.getUid()

            val updates: MutableMap<String, Any> = hashMapOf(
                "users/$uid/${App.ANDROID_ID}/number of steps" to ServerValue.increment(num),
                "users/$uid/${App.ANDROID_ID}/number of saves" to ServerValue.increment(1)
            )
            return App.it().appComponent.fDatabaseReference.updateChildren(updates)
        }

        fun writeReferrerBonus(num: Double): Task<Void> {
            val uid = RetrievedPreference.getUid()

            val updates: MutableMap<String, Any> = hashMapOf(
                "users/${RetrievedPreference.getReferrerUserId()}/${RetrievedPreference.getReferrerAndroidId()}/refBonus" to ServerValue.increment(num)
            )
            return App.it().appComponent.fDatabaseReference.updateChildren(updates)
        }
        fun writtenRefBonus(num: Double): Task<Void> {
            val uid = RetrievedPreference.getUid()

            val updates: MutableMap<String, Any> = hashMapOf(
                "users/$uid/${App.ANDROID_ID}/wrb" to num
            )
            return App.it().appComponent.fDatabaseReference.updateChildren(updates)
        }

        fun registrationUser(userId: String, name: String, email: String) {
            val childUpdates = hashMapOf<String, Any>(
                "users/$userId/${App.ANDROID_ID}/username" to name,
                "users/$userId/${App.ANDROID_ID}/email" to email,
                "users/$userId/${App.ANDROID_ID}/number of saves" to ServerValue.increment(0),
                "users/$userId/${App.ANDROID_ID}/number of steps" to ServerValue.increment(0),
                "users/$userId/${App.ANDROID_ID}/number of steps" to ServerValue.increment(0),
                "users/$userId/${App.ANDROID_ID}/ad viewed" to ServerValue.increment(0),
                "users/$userId/${App.ANDROID_ID}/vip" to ServerValue.increment(0),
                "users/$userId/${App.ANDROID_ID}/referrals" to ServerValue.increment(0)
            )
            App.it().appComponent.fDatabaseReference.updateChildren(childUpdates)
        }

        fun registrationReferral() {
            val myUserId = RetrievedPreference.getUid()

            val referrerUserId = RetrievedPreference.getReferrerUserId()
            val referrerAndroidId = RetrievedPreference.getReferrerAndroidId()

            val childUpdates = hashMapOf<String, Any>(
                "users/$myUserId/${App.ANDROID_ID}/referrer" to referrerUserId,

                "users/$referrerUserId/${referrerAndroidId}/referrals" to ServerValue.increment(1),
                "config/all referrals" to ServerValue.increment(1)
            )
            App.it().appComponent.fDatabaseReference.updateChildren(childUpdates)
        }
    }
}