package com.danilatyukov.linkedmoney.ui.auth

import com.danilatyukov.linkedmoney.data.local.preferences.AppPreferences
import com.danilatyukov.linkedmoney.data.vo.UserVO

interface AuthInteractor {

    var userDetails: UserVO
    var accessToken: String
    var submittedUsername: String
    var submittedPassword: String
    interface onAuthFinishedListener {
        fun onAuthSuccess()
        fun onAuthError(message: String)
        fun onUsernameError()
        fun onPasswordError(msg: String)
    }

    fun persistAccessToken(preferences: AppPreferences)
    fun persistUserDetails(preferences: AppPreferences)
}