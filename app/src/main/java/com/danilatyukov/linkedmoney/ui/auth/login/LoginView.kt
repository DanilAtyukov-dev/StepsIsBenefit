package com.danilatyukov.linkedmoney.data.auth

import com.danilatyukov.linkedmoney.BaseView

interface AuthView {
    fun showAuthError()
}

interface LoginView: BaseView, AuthView {
    fun showProgress()
    fun hideProgress()
    fun setEmailError()
    fun setPasswordError(msg: String)
    fun navigateToSignUp()
    fun navigateToHome()
    fun showInterface()
    fun hideInterface()

}