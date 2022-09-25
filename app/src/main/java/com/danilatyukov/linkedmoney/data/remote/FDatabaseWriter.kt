package com.danilatyukov.linkedmoney.data.remote

import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.appComponent
import com.danilatyukov.linkedmoney.data.local.preferences.RetrievedPreference
import com.google.firebase.database.ServerValue


class FDatabaseWriter() {
    companion object{
        fun writeSteps(num: Long) {
            val uid = RetrievedPreference.getUid()

            val updates: MutableMap<String, Any> = hashMapOf(
                "users/$uid/stepCount" to ServerValue.increment(num),
                "users/$uid/adCount" to ServerValue.increment(1)
            )
            App.it().appComponent.fDatabaseReference.updateChildren(updates)
        }

        fun registrationUser(userId: String, name: String, email: String) {
            val childUpdates = hashMapOf<String, Any>(
                "users/$userId/username" to name,
                "users/$userId/email" to email
            )
            App.it().appComponent.fDatabaseReference.updateChildren(childUpdates)
        }
    }

}