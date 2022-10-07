package com.danilatyukov.linkedmoney.data.remote

import android.util.Log
import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.appComponent
import com.danilatyukov.linkedmoney.appContext
import com.danilatyukov.linkedmoney.data.local.preferences.RetrievedPreference
import com.danilatyukov.linkedmoney.data.local.preferences.SavedPreference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.lang.NullPointerException

class FDatabaseReader() {
    init {
        appVersionListener()
        soprListener()
        krvListener()
        confirmStepsListener()
        adViewedListener()
        writtenRefBonusListener()
        referralsNumberListener()
        referralsBonusListener()
        kaListener()
    }

    private fun soprListener() {
        val stepsReference =
            App.it().appComponent.firebaseInstance.getReference("config").child("sopr")
        val stepPriceListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.value !is Double || dataSnapshot.value == null) return
                    SavedPreference.setSopr((dataSnapshot.value as Double).toFloat())

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ScoresVM", "loadPost:onCancelled", databaseError.toException())
            }
        }
        stepsReference.addValueEventListener(stepPriceListener)
    }

    private fun krvListener() {
        val stepsReference =
            App.it().appComponent.firebaseInstance.getReference("config").child("krv")
        val stepPriceListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.value !is Long || dataSnapshot.value == null) return
                    SavedPreference.setKrv((dataSnapshot.value as Long).toInt())

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ScoresVM", "loadPost:onCancelled", databaseError.toException())
            }
        }
        stepsReference.addValueEventListener(stepPriceListener)
    }

    private fun kaListener() {
        val stepsReference =
            App.it().appComponent.firebaseInstance.getReference("config").child("ka")
        val stepPriceListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.value !is Long || dataSnapshot.value == null) return
                SavedPreference.setKa((dataSnapshot.value as Long).toInt())

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ScoresVM", "loadPost:onCancelled", databaseError.toException())
            }
        }
        stepsReference.addValueEventListener(stepPriceListener)
    }

    private fun appVersionListener() {
        val stepsReference =
            App.it().appComponent.firebaseInstance.getReference("config").child("appVersion")
        val stepPriceListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.value !is Long || dataSnapshot.value == null) return

                if(dataSnapshot.value != App.version){
                    App.it().mainActivity?.lockInterface()
                }


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ScoresVM", "loadPost:onCancelled", databaseError.toException())
            }
        }
        stepsReference.addValueEventListener(stepPriceListener)
    }

    private fun adViewedListener() {
        val stepsReference = App.it().appComponent.firebaseInstance.getReference("users")
            .child(RetrievedPreference.getUid()).child(App.ANDROID_ID).child("ad viewed")
        val stepPriceListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.value !is Long || dataSnapshot.value == null) return
                    SavedPreference.adViewed((dataSnapshot.value as Long).toInt())

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ScoresVM", "loadPost:onCancelled", databaseError.toException())
            }
        }
        stepsReference.addValueEventListener(stepPriceListener)
    }

    private fun confirmStepsListener() {
        val stepsReference = App.it().appComponent.firebaseInstance.getReference("users")
            .child(RetrievedPreference.getUid()).child(App.ANDROID_ID).child("number of steps")
        val confirmStepsListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.value !is Long || dataSnapshot.value == null) return
                    SavedPreference.setConfirmSteps((dataSnapshot.value as Long).toInt())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ScoresVM", "loadPost:onCancelled", databaseError.toException())
            }
        }
        stepsReference.addValueEventListener(confirmStepsListener)
    }

    private fun referralsNumberListener() {
        val stepsReference = App.it().appComponent.firebaseInstance.getReference("users")
            .child(RetrievedPreference.getUid()).child(App.ANDROID_ID).child("referrals")
        val confirmStepsListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.value !is Long || dataSnapshot.value == null) return
                    SavedPreference.setReferralsNumber((dataSnapshot.value as Long).toInt())

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ScoresVM", "loadPost:onCancelled", databaseError.toException())
            }
        }
        stepsReference.addValueEventListener(confirmStepsListener)
    }

    private fun referralsBonusListener() {
        val stepsReference = App.it().appComponent.firebaseInstance.getReference("users")
            .child(RetrievedPreference.getUid()).child(App.ANDROID_ID).child("refBonus")
        val confirmStepsListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI

                if (dataSnapshot.value !is Double || dataSnapshot.value == null) return
                SavedPreference.setRefBonus((dataSnapshot.value as Double))

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ScoresVM", "loadPost:onCancelled", databaseError.toException())
            }
        }
        stepsReference.addValueEventListener(confirmStepsListener)
    }

    private fun writtenRefBonusListener() {
        val stepsReference = App.it().appComponent.firebaseInstance.getReference("users")
            .child(RetrievedPreference.getUid()).child(App.ANDROID_ID).child("wrb")
        val confirmStepsListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI

                if (dataSnapshot.value !is Double || dataSnapshot.value == null) return
                SavedPreference.setWrittenRefBonusSteps(dataSnapshot.value as Double)
                
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ScoresVM", "loadPost:onCancelled", databaseError.toException())
            }
        }
        stepsReference.addValueEventListener(confirmStepsListener)
    }

}

