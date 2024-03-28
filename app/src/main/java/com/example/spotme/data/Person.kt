package com.example.spotme.data

import java.util.Date

data class Person (
    val id: Long?,
    val name: String,
    val bio: String,
    val displayName: String,
    val profileImage: String = "No idea what type this should be",
    val debts: List<Debt> = listOf(),
    val paymentPreference:  PaymentType,
    //val createdDate: Date,
)