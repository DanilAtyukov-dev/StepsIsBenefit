package com.danilatyukov.linkedmoney.data.remote

import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.service.MessageApiService

abstract class BaseInteractor {

    private val service: MessageApiService = MessageApiService.getInstance()
    private val preferences = App.it().appComponent.appPreferences

    private fun authReset(){
        preferences.clear()
    }

}