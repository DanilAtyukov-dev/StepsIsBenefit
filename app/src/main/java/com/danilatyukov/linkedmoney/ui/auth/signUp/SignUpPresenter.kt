package com.danilatyukov.linkedmoney.ui.auth.signUp

import com.danilatyukov.linkedmoney.data.local.preferences.AppPreferences
import com.danilatyukov.linkedmoney.ui.auth.AuthInteractor
import com.danilatyukov.linkedmoney.ui.auth.signIn.SignUpInteractor
import com.danilatyukov.linkedmoney.ui.auth.signIn.SignUpInteractorImpl

interface SignUpPresenter {
    var preferences: AppPreferences
    fun executeSignUp(username: String, password: String)
}

class SignUpPresenterImpl(private val view: SignUpView) : SignUpPresenter,
    SignUpInteractor.OnSignUpFinishedListener,
    AuthInteractor.onAuthFinishedListener {
        private val interactor: SignUpInteractor = SignUpInteractorImpl()
    override var preferences = AppPreferences.create(view.context())

    override fun onSuccess() {
        interactor.getAuthorization(this)
    }

    override fun onError() {
        view.hideProgress()
        view.showSignUpError()
    }

    override fun onUsernameError() {
        view.hideProgress()
        view.showSignUpError()
    }


    override fun onPasswordError(msg: String) {
        view.hideProgress()
        view.setPasswordError(msg)
    }

    override fun executeSignUp(username: String, password: String) {
        view.showProgress()
        interactor.signUp(username, password, this)
    }

    override fun onAuthSuccess() {
        interactor.persistAccessToken(preferences)
        interactor.persistUserDetails(preferences)
        view.hideProgress()
        view.navigateToHome()
    }

    override fun onAuthError(message: String) {
        if (message.contains("failed to connect") || message.contains("Failed to connect") || message.contains("timeout")) {
            println("Отсутствует подключение")
            view.hideProgress()
            view.navigateToHome()
            return
        }

        view.hideProgress()
        view.showAuthError()
    }
}