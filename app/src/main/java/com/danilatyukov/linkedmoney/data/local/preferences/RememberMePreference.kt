package com.danilatyukov.linkedmoney.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import com.danilatyukov.linkedmoney.data.vo.Credentials
import com.danilatyukov.linkedmoney.data.vo.UserVO

class RememberMePreference {

    lateinit var preferences: SharedPreferences

    companion object {
        private val fileName = "REMEMBER_PREFERENCES"
        fun create(context: Context): RememberMePreference {
            val rememberPreferences = RememberMePreference()
            rememberPreferences.preferences = context.getSharedPreferences(fileName, 0)
            return rememberPreferences
        }
    }

    val credentials: Credentials
        get(): Credentials {
            return Credentials(
                preferences.getString("EMAIL", "")!!,
                preferences.getString("PASSWORD", "")!!
            )
        }

    fun storeCredentials(credentials: Credentials) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString("EMAIL", credentials.username).apply()
        editor.putString("PASSWORD", credentials.password).apply()
    }

    val policyIsShown: Boolean
        get(): Boolean {
            return preferences.getBoolean("POLICY", false)
        }
    fun policyShown() {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putBoolean("POLICY", true).apply()
    }


}