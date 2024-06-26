package com.example.spotme.data

import java.util.Date

data class Profile(
    val id: Long?,
    val name: String,
    val description: String,
    val amount: Double,
    //val profileImage: String = "No idea what type this should be",
    val debts: List<Debt>?,
    val paymentPreference: PaymentType,
    val createdDate: Date = Date(0),
    val hidden: Boolean = false,
)