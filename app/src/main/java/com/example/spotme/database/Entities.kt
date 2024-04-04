package com.example.spotme.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date

@Entity
data class Profile(
    @PrimaryKey val profileId: Long?,
    val name: String,
    val description: String,
    val paymentPreference: String,
    val totalDebt: Double,
    val createdDate: Date,
)

@Entity
data class Debt(
    @PrimaryKey val debtId: Long?,
    val f_profile_id: Long?,
    val name: String,
    val totalDebt: Double,
    val description: String,
    val canceled: Boolean,
    val createdDate: Date,
)

@Entity
data class Transaction(
    @PrimaryKey val transactionId: Long?,
    val f_debt_id: Long?, //Foreign Key
    val createdDate: Date,
    val amount: Double,
    val description: String,
    val canceled: Boolean,
)

//Relationship: Profile -< Debt
data class ProfileWithDebts(
    @Embedded val profile: Profile,
    @Relation(
        parentColumn = "profileId",
        entityColumn = "f_profile_id"
    )
    val debts: List<Debt>
)

//Relationship: Debt -< Transaction
data class DebtWithTransactions(
    @Embedded val debt: Debt,
    @Relation(
        parentColumn = "debtId",
        entityColumn = "f_debt_id",
    )
    val transactions: List<Transaction>
)