package com.danilatyukov.linkedmoney.ui.login

import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.appComponent
import com.danilatyukov.linkedmoney.data.local.preferences.AppPreferences
import com.danilatyukov.linkedmoney.data.remote.request.UserRequestObject
import com.danilatyukov.linkedmoney.data.vo.Credentials
import com.danilatyukov.linkedmoney.data.vo.UserVO
import com.danilatyukov.linkedmoney.service.MessageApiService
import com.danilatyukov.linkedmoney.ui.auth.AuthInteractor
import com.danilatyukov.linkedmoney.utils.CredentialsValidator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_auth.*


interface LoginInteractor : AuthInteractor {
    interface OnDetailsRetrievalFinishedListener {
        fun onDetailsRetrievalSuccess()
        fun onDetailsRetrievalError(message: String)
    }
    fun login(username: String, password: String, listener: AuthInteractor.onAuthFinishedListener)
    fun retrieveDetails(preferences: AppPreferences, listener: OnDetailsRetrievalFinishedListener)
}

class LoginInteractorImpl : LoginInteractor {

    override lateinit var userDetails: UserVO

    override lateinit var accessToken: String
    override lateinit var submittedUsername: String
    override lateinit var submittedPassword: String

    private val service: MessageApiService = MessageApiService.getInstance()

   override fun login(username: String, password: String, listener: AuthInteractor.onAuthFinishedListener) {


       val passValidRes = CredentialsValidator.isPasswordValid(password)
       when {
           !CredentialsValidator.isEmailValid(username) -> listener.onUsernameError()
           passValidRes != "ok" -> listener.onPasswordError(passValidRes)


            else -> {

                submittedUsername = username
                submittedPassword = password

                App.it().appComponent.rememberMePreference.storeCredentials(Credentials(submittedUsername, submittedPassword))

                val requestObject = UserRequestObject(username, password)

                service.login(requestObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ res ->
                        if (res.code() != 403) {
                            accessToken = res.headers()["Authorization"] as String
                            listener.onAuthSuccess()
                        } else {
                            listener.onAuthError(res.message())
                        }
                    }, { error ->
                        println("error")
                        listener.onAuthError(error.message!!)
                        error.printStackTrace()
                    })
            }
        }
    }

    override fun retrieveDetails(
        preferences: AppPreferences,
        listener: LoginInteractor.OnDetailsRetrievalFinishedListener
    ) {
        service.echoDetails(preferences.accessToken as String)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                userDetails = res
                listener.onDetailsRetrievalSuccess()

            }, { error ->
                println("error retrieveDetails")
                listener.onDetailsRetrievalError(error.message!!)
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