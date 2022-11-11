package com.danilatyukov.linkedmoney.utils

import android.text.TextUtils

class CredentialsValidator {
    companion object{
        fun isPasswordValid(str: String): String{
            if (str.length < 6) return "минимальная длина пароля - 6 символов"
            if (str.filter { it.isDigit() }.firstOrNull() == null) return "пароль должен состоять из букв нижнего и верхнего регистра и цифр"
            if (str.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null) return "пароль должен состоять из букв нижнего и верхнего регистра и цифр"
            if (str.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) return "пароль должен состоять из букв нижнего и верхнего регистра и цифр"
            //if (str.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return "спец символ"
            return "ok"
        }
        fun isEmailValid(str: String): Boolean{
            return !TextUtils.isEmpty(str) && android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()
        }
    }
}