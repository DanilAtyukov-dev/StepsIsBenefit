package com.danilatyukov.linkedmoney.model

import android.content.Context
import android.os.RemoteException
import android.util.Log
import com.danilatyukov.linkedmoney.data.local.preferences.SavedPreference.Companion.setGoogleReferrerId
import com.danilatyukov.linkedmoney.data.local.preferences.RetrievedPreference.Companion.getReferrerId
import com.danilatyukov.linkedmoney.data.local.preferences.SavedPreference.Companion.setReferrerUserId
import com.danilatyukov.linkedmoney.data.local.preferences.SavedPreference.Companion.setReferrerAndroidId
import com.android.installreferrer.api.InstallReferrerClient
import com.danilatyukov.linkedmoney.data.local.preferences.RetrievedPreference
import com.danilatyukov.linkedmoney.data.local.preferences.SavedPreference
import com.google.firebase.auth.FirebaseAuth
import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.model.ReferralProgram
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails
import android.widget.Toast
import com.danilatyukov.linkedmoney.data.remote.FDatabaseWriter
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport

class ReferralProgram(context: Context?) {

    var referrerClient: InstallReferrerClient

    var referrer = "non"

    companion object {
        var TAG = "ReferralProgram"
        val referralLink: String
        get() = "https://play.google.com/store/apps/details?id=com.danilatyukov.linkedmoney&referrer=" + FirebaseAuth.getInstance().uid + App.ANDROID_ID
    }

    fun divideReferrerLink() {
        var id = getReferrerId()
        if (id == "non" || id.length != 44) id = "00000000000000000000000000000000000000000000"
        setReferrerUserId(id.substring(0, 28))
        setReferrerAndroidId(id.substring(28, 44))

        FDatabaseWriter.registrationReferral()
    }

    init {
        Log.e(TAG, "response.getInstallReferrer() ")
        // on below line we are building our install referrer client and building it.
        referrerClient = InstallReferrerClient.newBuilder(context).build()

        // on below line we are starting its connection.
        referrerClient.startConnection(object : InstallReferrerStateListener {
            override fun onInstallReferrerSetupFinished(responseCode: Int) {
                // this method is called when install referer setup is finished.
                when (responseCode) {
                    InstallReferrerClient.InstallReferrerResponse.OK -> {
                        // this case is called when the status is OK and
                        var response: ReferrerDetails? = null
                        try {
                            response = referrerClient.installReferrer

                            referrer = response.installReferrer

                            setGoogleReferrerId(referrer)

                            divideReferrerLink()
                        } catch (e: RemoteException) {
                            FirebaseCrashlytics.getInstance().recordException(e)
                        }
                    }
                }
            }

            override fun onInstallReferrerServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                //Toast.makeText(context, "Service disconnected..", Toast.LENGTH_SHORT).show();
            }
        })
    }
}