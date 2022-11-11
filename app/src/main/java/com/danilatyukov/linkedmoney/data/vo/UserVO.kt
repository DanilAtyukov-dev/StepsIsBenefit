package com.danilatyukov.linkedmoney.data.vo

data class UserVO(
    val id: Long,
    val email: String?,
    val referrerId: Long?,
    val allMoney: Float,
    val stepCost: Float?,
    val allRefBonus: Float,
    val allReferrals: Long,
    val allSteps: Long,
    val accountStatus: String?,
    val privilegeStatus: String?,
    val createdAt: String?
)
