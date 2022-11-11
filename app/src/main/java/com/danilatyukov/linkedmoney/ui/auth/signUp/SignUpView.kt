package com.danilatyukov.linkedmoney.ui.auth.signUp

import com.danilatyukov.linkedmoney.BaseView
import com.danilatyukov.linkedmoney.data.auth.AuthView

interface SignUpView: BaseView, AuthView {
    fun showProgress()
    fun showSignUpError()
    fun hideProgress()
    fun setUsernameError()
    fun setPasswordError(msg: String)
    fun navigateToHome()
}