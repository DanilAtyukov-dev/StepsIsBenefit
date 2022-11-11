package com.danilatyukov.linkedmoney.data.vo

data class StatisticVO(
    val id: Long,
    val ownerId: Long?,
    val flowStatId: Long?,
    val numSteps: Long?,
    val numAdsShown: Long?,
    val stepCost: Double?,
    val totalCost: Double?,
    val createdAt: String,
)
