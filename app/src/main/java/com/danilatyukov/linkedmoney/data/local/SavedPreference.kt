package com.danilatyukov.linkedmoney.data.local

import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.AppComponent
import com.danilatyukov.linkedmoney.appComponent

class SavedPreference {

    companion object{
        private val appComponent: AppComponent = App.it().appComponent

        fun setEmail(email: String){
            appComponent.editor.putString("email", email)
            appComponent.editor.apply()
            println("email is saved "+ appComponent.editor.commit())
        }
        fun setUsername(name: String){
            appComponent.editor.putString("username", name)
            appComponent.editor.apply()
            println("username is saved "+ appComponent.editor.commit())
        }
        fun setUid(uid: String){
            appComponent.editor.putString("uid", uid)
            appComponent.editor.apply()
            println("Uid is saved "+ appComponent.editor.commit())
        }
        fun adViewed(){
            val ads = RetrievedPreference.getAds()

            appComponent.editor.putInt("ads", ads + 1)
            appComponent.editor.apply()
            println("ads count is updated "+ appComponent.editor.commit())
        }
    }
}