package com.example.spotme.data

import java.util.Date

data class Transaction(
    val id: Long?,
    val amount: Double,
    val description: String,
    val canceled: Boolean,
    val date: Date = Date(0),
    )
