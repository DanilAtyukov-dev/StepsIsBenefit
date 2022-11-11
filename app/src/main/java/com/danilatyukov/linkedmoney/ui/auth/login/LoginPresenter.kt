package com.danilatyukov.linkedmoney.ui.login

import android.content.Intent
import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.data.auth.LoginView
import com.danilatyukov.linkedmoney.data.local.preferences.AppPreferences
import com.danilatyukov.linkedmoney.data.vo.UserVO
import com.danilatyukov.linkedmoney.ui.auth.AuthInteractor
import com.danilatyukov.linkedmoney.ui.auth.login.LoginActivity

interface LoginPresenter {
    fun executeLogin(username: String, password: String)
    fun retrieveDetails()
}

class LoginPresenterImpl(private val view: LoginView) : LoginPresenter,
    AuthInteractor.onAuthFinishedListener,
    LoginInteractor.OnDetailsRetrievalFinishedListener {
    private val interactor: LoginInteractor = LoginInteractorImpl()
    private val preferences: AppPreferences = AppPreferences.create(view.context())

    override fun onPasswordError(msg: String) {
        view.hideProgress()
        view.setPasswordError(msg)
    }

    override fun onUsernameError() {
        view.hideProgress()
        view.setEmailError()
    }

    override fun onAuthSuccess() {
        interactor.persistAccessToken(preferences)
        interactor.retrieveDetails(preferences, this)
    }

    override fun onAuthError(message: String) {
        println("onAuthError")

        if (message.contains("failed to connect") || message.contains("Failed to connect") || message.contains("timeout") || message.contains("address associated")) {
            println("Отсутствует подключение")
            view.hideProgress()
            view.navigateToHome()
            return
        }

        view.showAuthError()
        view.hideProgress()
    }

    override fun onDetailsRetrievalSuccess() {
        interactor.persistUserDetails(preferences)
        view.hideProgress()
        view.navigateToHome()
    }

    override fun onDetailsRetrievalError(message: String) {
        println("message $message")
        if (message.contains(" 403")){
            println("Сброс авторизации")
            view.showInterface()
            resetAuth()
            return
        }
        if (message.contains("failed to connect") || message.contains("Failed to connect") || message.contains("timeout")) {
            println("Отсутствует подключение")
            view.hideProgress()
            view.navigateToHome()
            return
        }
        interactor.retrieveDetails(preferences, this)
    }

    override fun executeLogin(username: String, password: String) {
        view.showProgress()
        interactor.login(username, password, this)
    }

    override fun retrieveDetails() {
        view.hideInterface()
        interactor.retrieveDetails(preferences, this)
    }

    fun resetAuth() {
        App.it().appComponent.appPreferences.clear()
        //val intent = Intent(App.it(), LoginActivity::class.java)
        //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
        //App.it().startActivity(intent)
    }
}