package com.example.spotme.data

import java.util.Date

data class Debt (
    val id: Long?,
    val userID: Long?,
    val name: String,
    val description: String,
    val transactions: List<Transaction>?,
    val amount: Double,
    val canceled: Boolean,
    val createdDate: Date = Date(0),
    val hidden: Boolean = false,
)