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
    @ColumnInfo(name="name")
    val name: String,
    @ColumnInfo(name="bio")
    val bio: String,
    @ColumnInfo(name="payment_preference")
    val paymentPreference: String,
    @ColumnInfo("debt_issue_date")
    val date: Date
)

@Entity
data class Debt(
    @PrimaryKey val debtId: Long?,
    @ColumnInfo(name="f_profile_id") //Foreign Key
    val profileIdF: Long?,
    @ColumnInfo(name="overall_debt")
    val overallDebt: Double,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name="status")
    val canceled: Boolean
)

@Entity
data class Transaction(
    @PrimaryKey val transactionId: Long?,
    @ColumnInfo(name="f_debt_id") //Foreign Key
    val debtIdF: Long?,
    @ColumnInfo(name="date")
    val date: Date,
    @ColumnInfo(name="amount_owed")
    val amount: Double,
    @ColumnInfo(name = "description")
    val description: String,
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