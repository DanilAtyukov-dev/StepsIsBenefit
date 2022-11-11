package com.danilatyukov.linkedmoney.networkListeners


import android.content.Intent
import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.data.remote.interactors.StatisticInteractor
import com.danilatyukov.linkedmoney.data.vo.UserVO
import com.danilatyukov.linkedmoney.ui.auth.login.LoginActivity

class StatisticListener: StatisticInteractor.onStatisticFinishedListener {
    override fun onSuccess(userDetails: UserVO) {

        println("Success ${userDetails}" )

        //App.it().appComponent.scoresPreferences.setStepCost(userDetails.stepCost!!.toFloat())

        App.it().appComponent.appPreferences.storeUserDetails(userDetails)
    }

    override fun onError(message: String) {
        if (message.contains(" 403")) {
            App.it().appComponent.appPreferences.clear()
            val intent = Intent(App.it(), LoginActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            App.it().startActivity(intent)
        }
        if (message.contains("failed to connect") || message.contains("Failed to connect") || message.contains("timeout")){
            App.it().appComponent.appPreferences.incConfirmSteps(
                App.it().appComponent.appPreferences.currentSteps
            )
        }
    }
}