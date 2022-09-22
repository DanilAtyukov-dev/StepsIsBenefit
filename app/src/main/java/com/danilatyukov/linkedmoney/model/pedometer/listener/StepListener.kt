package com.danilatyukov.linkedmoney.model.pedometer.listener

/**
 * Created by Govind on 05/25/2018.
 */
interface StepListener {
    fun step(timeNs: Long)
}