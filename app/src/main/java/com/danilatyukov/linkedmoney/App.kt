package com.danilatyukov.linkedmoney

import android.app.Application
import android.content.Context


class App : Application() {
    lateinit var appComponent: AppComponent
    lateinit var context: Context

    companion object {
        lateinit var app: Application
        fun it(): Application {
            return app
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        appComponent = DaggerAppComponent.create()
        context = this
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }
val Context.appContext: Context
    get() = when (this) {
        is App -> context
        else -> this.applicationContext.appContext
    }