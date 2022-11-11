package com.danilatyukov.linkedmoney.data.vo

data class FlowStatVO(
    val id: Long,
    val ownerId: Long,
    val stats: ArrayList<StatisticVO>,
    val date: String
)

