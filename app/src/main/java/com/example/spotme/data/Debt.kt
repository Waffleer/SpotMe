package com.example.spotme.data

data class Debt (
    val id: Long?,
    val userID: Long?,
    val name: String?,
    val description: String?,
    val transactions: List<Transaction> = listOf(),
    val amount: Double,
    val hidden: Boolean = false,
)