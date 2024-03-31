package com.example.spotme.data

data class Group ( //For our groups functionality
    val id: Long?,
    val name: String,
    val description: String?,
    val profiles: List<Profile>,
    val transactions: Debt,
    val debts: List<Debt> = listOf(),
    val hidden: Boolean = false,
)