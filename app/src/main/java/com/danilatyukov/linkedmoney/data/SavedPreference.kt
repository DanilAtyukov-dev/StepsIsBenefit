package com.danilatyukov.linkedmoney.data


import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.AppComponent
import com.danilatyukov.linkedmoney.appComponent


class SavedPreference {

    companion object{
        private val appComponent: AppComponent = App.it().appComponent

        fun setEmail(email: String){
            appComponent.editor.putString("email", email)
            appComponent.editor.commit()
            appComponent.editor.apply()
            println("email is saved"+ appComponent.editor.commit())
        }
        fun setUsername(name: String){
            appComponent.editor.putString("username", name)
          println("username is saved"+ appComponent.editor.commit())
            appComponent.editor.apply()
        }
    }

}