package com.example.spotme.data

data class Debt (
    val id: Long?,
    val userID: Long?,
    val transactions: List<Transaction> = listOf(),
)