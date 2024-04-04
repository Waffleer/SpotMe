package com.example.spotme.data

import java.util.Date

data class Transaction(
    val id: Long?,
    val debtId: Long?,
    val amount: Double,
    val description: String,
    val canceled: Boolean,
    val createdDate: Date = Date(0),
    )
