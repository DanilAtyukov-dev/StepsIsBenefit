package com.danilatyukov.linkedmoney

import android.content.Context

interface BaseView  {
    fun context(): Context

    fun showToast(msg: String)
}