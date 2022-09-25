package com.danilatyukov.linkedmoney.data.local.preferences

import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.AppComponent
import com.danilatyukov.linkedmoney.appComponent

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
        fun adViewed(){
            val ads = RetrievedPreference.getAds()

            editor.putInt("ads", ads + 1)
            editor.apply()
            editor.commit()
        }
    }
}