package com.danilatyukov.linkedmoney.data.remote.repository

import android.content.Context
import com.danilatyukov.linkedmoney.data.local.preferences.AppPreferences
import com.danilatyukov.linkedmoney.data.vo.UserVO
import com.danilatyukov.linkedmoney.service.MessageApiService
import io.reactivex.Observable

class UserRepository(ctx: Context) {
    private val preferences: AppPreferences = AppPreferences.create(ctx)
    private val service: MessageApiService = MessageApiService.getInstance()

    fun echoDetails(): Observable<UserVO> {
        return service.echoDetails(preferences.accessToken as String)
    }
}