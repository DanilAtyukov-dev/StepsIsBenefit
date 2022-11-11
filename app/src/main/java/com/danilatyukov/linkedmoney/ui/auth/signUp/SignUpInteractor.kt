package com.danilatyukov.linkedmoney.ui.auth.signIn

import android.text.TextUtils
import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.data.local.preferences.AppPreferences
import com.danilatyukov.linkedmoney.data.remote.request.UserRequestObject
import com.danilatyukov.linkedmoney.data.vo.Credentials
import com.danilatyukov.linkedmoney.data.vo.UserVO
import com.danilatyukov.linkedmoney.service.MessageApiService
import com.danilatyukov.linkedmoney.ui.auth.AuthInteractor
import com.danilatyukov.linkedmoney.utils.CredentialsValidator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SignUpInteractor: AuthInteractor {
    interface OnSignUpFinishedListener {
        fun onSuccess()
        fun onUsernameError()
        fun onPasswordError(msg: String)
        fun onError()
    }
    fun signUp(username: String, password: String, listener: OnSignUpFinishedListener)
    fun getAuthorization(listener: AuthInteractor.onAuthFinishedListener)
}

class SignUpInteractorImpl: SignUpInteractor {
    override lateinit var userDetails: UserVO
    override lateinit var accessToken: String
    override lateinit var submittedUsername: String
    override lateinit var submittedPassword: String
    private val service: MessageApiService = MessageApiService.getInstance()



    override fun signUp(
        username: String,
        password: String,
        listener: SignUpInteractor.OnSignUpFinishedListener
    ) {
        submittedUsername = username
        submittedPassword = password
        val userRequestObject = UserRequestObject(username, password)

        val passValidRes = CredentialsValidator.isPasswordValid(password)

        when {

            !CredentialsValidator.isEmailValid(username) -> listener.onUsernameError()
             passValidRes != "ok" -> listener.onPasswordError(passValidRes)

            //TextUtils.isEmpty(username) -> listener.onUsernameError()
            //TextUtils.isEmpty(password) -> listener.onPasswordError()
            else -> {
                App.it().appComponent.rememberMePreference.storeCredentials(Credentials(submittedUsername, submittedPassword))

                service.createUser(userRequestObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        res ->
                        userDetails = res
                        listener.onSuccess()
                    }, {
                        error -> listener.onError()

                        error.printStackTrace()
                    })
            }
        }
    }

    override fun getAuthorization(listener: AuthInteractor.onAuthFinishedListener) {
        val userRequestObject = UserRequestObject(submittedUsername, submittedPassword)
        service.login(userRequestObject)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                res ->
                accessToken = res.headers()["Authorization"] as String
                listener.onAuthSuccess()
            }, {
                error -> listener.onAuthError(error.message!!)
                error.printStackTrace()
            })
    }

    override fun persistAccessToken(preferences: AppPreferences) {
        preferences.storeAccessToken(accessToken)
    }

    override fun persistUserDetails(preferences: AppPreferences) {
        preferences.storeUserDetails(userDetails)
    }

}