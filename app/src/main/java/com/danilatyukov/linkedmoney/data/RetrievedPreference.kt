package com.danilatyukov.linkedmoney.data


import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.AppComponent
import com.danilatyukov.linkedmoney.appComponent


class RetrievedPreference {
    companion object{
        private val appComponent: AppComponent = App.it().appComponent

        fun getEmail(): String{
            return appComponent.sp.getString("email", "unauthorized").toString()
        }
        fun getUsername(): String{
            return appComponent.sp.getString("username", "unauthorized").toString()
        }
    }
}