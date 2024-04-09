package com.example.spotme.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date

// <--- ENTITIES --->
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

@Entity(primaryKeys = ["debtId","transactionId"])
data class DebtTransactionsCrossRef(
    val debtId: Long?,
    val transactionId: Long?
)


// <--- RELATIONAL OBJECTS --->

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

data class ProfileWithEverything(
    @Embedded val profile: Profile,
    @Relation(
        entity = Debt::class,
        parentColumn = "profileId",
        entityColumn = "f_profile_id"
    )
    val debtsWithTransactions: List<DebtWithTransactions>

)

// <--- SPECIFIC TUPLES --->

data class ProfileDebtTuple(
    @ColumnInfo(name = "profileId") val profileId: Long?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "totalDebt") val totalDebt: Double,
)