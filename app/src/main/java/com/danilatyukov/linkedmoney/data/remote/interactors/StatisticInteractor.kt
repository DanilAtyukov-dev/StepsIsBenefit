package com.danilatyukov.linkedmoney.data.remote.interactors

import android.content.Intent
import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.MainActivity
import com.danilatyukov.linkedmoney.data.local.preferences.AppPreferences
import com.danilatyukov.linkedmoney.data.remote.request.StatisticRequestObject
import com.danilatyukov.linkedmoney.data.vo.StatisticVO
import com.danilatyukov.linkedmoney.data.vo.UserVO
import com.danilatyukov.linkedmoney.service.MessageApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface StatisticInteractor {
    interface onStatisticFinishedListener {
        fun onSuccess(userDetails: UserVO)
        fun onError(message: String)
    }

    fun create(stepsNum: Long = App.it().appComponent.appPreferences.currentSteps - App.it().appComponent.appPreferences.sentSteps)
}

class StatisticInteractorImpl(
    val preferences: AppPreferences,
    private val listener: StatisticInteractor.onStatisticFinishedListener
) : StatisticInteractor {

    private val service: MessageApiService = MessageApiService.getInstance()

    override fun create(stepsNum: Long) {
        val statisticRequestObject = StatisticRequestObject(stepsNum, preferences.currentAds)
        service.createStatistic(statisticRequestObject, preferences.accessToken as String)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                listener.onSuccess(res)

                //TODO разобраться, когда данные были отправлены а когда - нет и сделать так, чтобы если данные не дошли - не удалять их из кэша
                preferences.incSentSteps(stepsNum)
                preferences.clearCurrentAds()

            }, { error ->

                listener.onError(error.message!!)
                error.printStackTrace()
                preferences.incSentSteps(stepsNum)
                preferences.clearCurrentAds()
            })
    }



}