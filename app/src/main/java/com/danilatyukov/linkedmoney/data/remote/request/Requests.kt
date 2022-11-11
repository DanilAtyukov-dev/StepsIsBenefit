package com.danilatyukov.linkedmoney.data.remote.request

data class UserRequestObject(
    val username: String,
    val password: String
)

data class StatisticRequestObject(
    val numSteps: Long,
    val numAdsShown: Long
)