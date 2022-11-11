package com.danilatyukov.linkedmoney.data.remote.repository

import android.content.Context
import com.danilatyukov.linkedmoney.data.local.preferences.AppPreferences
import com.danilatyukov.linkedmoney.data.remote.request.StatisticRequestObject
import com.danilatyukov.linkedmoney.data.vo.StatisticVO
import com.danilatyukov.linkedmoney.data.vo.UserVO
import com.danilatyukov.linkedmoney.service.MessageApiService
import io.reactivex.Observable

class StatisticRepository(ctx: Context) {
    private val preferences: AppPreferences = AppPreferences.create(ctx)
    private val service: MessageApiService = MessageApiService.getInstance()

    fun create(statisticRequestObject: StatisticRequestObject): Observable<UserVO>{
        return service.createStatistic(statisticRequestObject, preferences.accessToken as String)
    }

    fun last():Observable<StatisticVO>{
        return service.lastStatistic(preferences.accessToken as String)
    }
}